/* -*- coding: utf-8 -*- 
 ******************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 * ******************************************************************
 */
   
#include "ilp.h"

char *ilpObj_Id = "$Id: ilpObj.c 1057 2011-08-19 12:14:07Z queinnec $";

/** Predefined classes */

extern struct ILP_Class ILP_object_Object_class;

struct ILP_Class ILP_object_Class_class = {
     &ILP_object_Class_class,
     { { &ILP_object_Object_class,
         "Class",
         1,
         &ILP_object_super_field,
         2,
         { ILP_print,
           ILP_classOf } } }
};

struct ILP_Class ILP_object_Object_class = {
     &ILP_object_Class_class,
     { { NULL,
         "Object",
         0,
         NULL,
         2,
         { ILP_print,
           ILP_classOf } } }
};

struct ILP_Class ILP_object_Method_class = {
     &ILP_object_Class_class,
     { { &ILP_object_Object_class,
         "Method",
         0,
         NULL,
         3,
         { ILP_print,
           ILP_classOf } } }
};

struct ILP_Class ILP_object_Field_class = {
     &ILP_object_Class_class,
     { { &ILP_object_Object_class,
         "Field",
         1,
         &ILP_object_defining_class_field,
         2,
         { ILP_print,
           ILP_classOf } } }
};

struct ILP_Class ILP_object_Closure_class = {
     &ILP_object_Class_class,
     { { &ILP_object_Object_class,
         "Function",
         3,
         NULL,
         2,
         { ILP_print,
           ILP_classOf } } }
};

struct ILP_Class ILP_object_Box_class = {
     &ILP_object_Class_class,
     { { &ILP_object_Object_class,
         "Box",
         1,
         &ILP_object_value_field,
         2,
         { ILP_print,
           ILP_classOf } } }
};

struct ILP_Class ILP_object_Integer_class = {
     &ILP_object_Class_class,
     { { &ILP_object_Object_class,
         "Integer",
         0,
         NULL,
         2,
         { ILP_print,
           ILP_classOf } } }
};

struct ILP_Class ILP_object_Float_class = {
     &ILP_object_Class_class,
     { { &ILP_object_Object_class,
         "Float",
         0,
         NULL,
         2,
         { ILP_print,
           ILP_classOf } } }
};

struct ILP_Class ILP_object_Boolean_class = {
     &ILP_object_Class_class,
     { { &ILP_object_Object_class,
         "Boolean",
         0,
         NULL,
         2,
         { ILP_print,
           ILP_classOf } } }
};

struct ILP_Class ILP_object_String_class = {
     &ILP_object_Class_class,
     { { &ILP_object_Object_class,
         "String",
         0,
         NULL,
         2,
         { ILP_print,
           ILP_classOf } } }
};

struct ILP_Class ILP_object_Exception_class = {
     &ILP_object_Class_class,
     { { &ILP_object_Object_class,
         "Exception",
         0,
         NULL,
         2,
         { ILP_print,
           ILP_classOf } } }
};

/** Predefined fields
 *
 * Fields yielding non-ILP values are not considered as fields.
 */

struct ILP_Field ILP_object_super_field = {
     &ILP_object_Field_class,
     { { &ILP_object_Class_class,
         NULL,
         "super",
         0 } }
};

struct ILP_Field ILP_object_defining_class_field = {
     &ILP_object_Field_class,
     { { &ILP_object_Field_class,
         NULL,
         "defining_class",
         0 } }
};

struct ILP_Field ILP_object_previous_field_field = {
     &ILP_object_Field_class,
     { { &ILP_object_Field_class,
         &ILP_object_defining_class_field,
         "previous_field",
         1 } }
};

struct ILP_Field ILP_object_class_defining_field = {
     &ILP_object_Field_class,
     { { &ILP_object_Method_class,
         NULL,
         "class_defining",
         0 } }
};

struct ILP_Field ILP_object_value_field = {
     &ILP_object_Field_class,
     { { &ILP_object_Box_class,
         NULL,
         "value",
         0 } }
};

/** Predefined methods 
 * 
 *     o.print() prints the object o
 *     o.getClass() yields the class of o
 */

struct ILP_Method ILP_object_print_method = {
     &ILP_object_Method_class,
     { { &ILP_object_Object_class,
         "print",
         1,       /* arity */
         0        /* offset */
     } }
};

struct ILP_Method ILP_object_classOf_method = {
     &ILP_object_Method_class,
     { { &ILP_object_Object_class,
         "classOf",
         1,       /* arity */
         1        /* offset */
     } }
};

/** Boolean
 * 
 */

struct ILP_Object ILP_object_true = {
     &ILP_object_Boolean_class,
     { ILP_BOOLEAN_TRUE_VALUE }
};

struct ILP_Object ILP_object_false = {
     &ILP_object_Boolean_class,
     { ILP_BOOLEAN_FALSE_VALUE }
};

/** Exceptions
 *
 * There is only one current exception. This prevents multi-threading.
 */

static struct ILP_Exception ILP_the_exception =  {
     &ILP_object_Exception_class,
     { { "", 
         { NULL } } }
};

/** Global variables
 *  -- current exception catcher
 *  -- current exception
 */

static struct ILP_catcher ILP_the_original_catcher = {
     NULL
};
struct ILP_catcher *ILP_current_catcher = &ILP_the_original_catcher;

ILP_Object ILP_current_exception = NULL;

/** Raise exception. */

ILP_Object
ILP_throw (ILP_Object exception)
{
     ILP_current_exception = exception;
     if ( ILP_current_catcher == &ILP_the_original_catcher ) {
          ILP_die("No current catcher!");
     };
     longjmp(ILP_current_catcher->_jmp_buf, 1);
     /** UNREACHABLE */
     return ILP_die("longjmp botch");
}

/** Install a new catcher */

void
ILP_establish_catcher (struct ILP_catcher *new_catcher)
{
     new_catcher->previous = ILP_current_catcher;
     ILP_current_catcher = new_catcher;
}

/** Reset an old catcher. */

void
ILP_reset_catcher (struct ILP_catcher *catcher)
{
     ILP_current_catcher = catcher;
}

/** Signal an error. */

ILP_Object
ILP_error (char *message)
{
     snprintf(ILP_the_exception._content.asException.message,
              ILP_EXCEPTION_BUFFER_LENGTH,
              "Error: %s\n",
              message);
     fprintf(stderr, "%s", ILP_the_exception._content.asException.message);
     ILP_the_exception._content.asException.culprit[0] = NULL;
     return ILP_throw((ILP_Object) &ILP_the_exception);
}

/** Signal a type error. */

ILP_Object
ILP_domain_error (char *message, ILP_Object o)
{
     snprintf(ILP_the_exception._content.asException.message,
              ILP_EXCEPTION_BUFFER_LENGTH,
              "Domain error: %s\nCulprit: 0x%p\n",
              message, (void*) o);
     fprintf(stderr, "%s", ILP_the_exception._content.asException.message);
     ILP_the_exception._content.asException.culprit[0] = o;
     ILP_the_exception._content.asException.culprit[1] = NULL;
     return ILP_throw((ILP_Object) &ILP_the_exception);
}

/** Abort abruptly the whole computation. */

ILP_Object
ILP_die (char *message)
{
     fputs(message, stderr);
     fputc('\n', stderr);
     fflush(stderr);
     exit(EXIT_FAILURE);
}

/** Check whether o is of class class. */

int /* boolean */
ILP_is_a (ILP_Object o, ILP_Class class)
{
     ILP_Class oclass = o->_class;
     if ( oclass == class ) {
          return 1;
     } else {
          oclass = oclass->_content.asClass.super;
          /* Object's superclass is NULL */
          while ( oclass ) {
               if ( oclass == class ) {
                    return 1;
               };
               oclass = oclass->_content.asClass.super;
          }
          return 0;
     }
}

static int
ILP_is_subclass_of (ILP_Class oclass, ILP_Class otherclass)
{
     if ( oclass == otherclass ) {
          return 1;
     } else {
          oclass = oclass->_content.asClass.super;
          /* Object's superclass is NULL */
          while ( oclass ) {
               if ( oclass == otherclass ) {
                    return 1;
               };
               oclass = oclass->_content.asClass.super;
          }
          return 0;
     }
}          

/** Find the appropriate method */

ILP_general_function
ILP_find_method (ILP_Object receiver,
                 ILP_Method method,
                 int argc)
{
     ILP_Class oclass = receiver->_class;
     if ( ! ILP_is_subclass_of(oclass, 
                               method->_content.asMethod.class_defining) ) {
          /* Signaler une absence de méthode */
          snprintf(ILP_the_exception._content.asException.message,
                   ILP_EXCEPTION_BUFFER_LENGTH,
                   "No such method %s\nCulprit: 0x%p\n",
                   method->_content.asMethod.name, 
                   (void*) receiver);
          /*DEBUG*/
          fprintf(stderr, "%s", ILP_the_exception._content.asException.message);
          ILP_the_exception._content.asException.culprit[0] = receiver;
          ILP_the_exception._content.asException.culprit[1] = 
               (ILP_Object) method;
          ILP_the_exception._content.asException.culprit[2] = NULL;
          ILP_throw((ILP_Object) &ILP_the_exception);
          /* UNREACHED */
          return NULL;          
     };
     if ( argc != method->_content.asMethod.arity ) {
          /* Signaler une erreur d'arité */
          snprintf(ILP_the_exception._content.asException.message,
                   ILP_EXCEPTION_BUFFER_LENGTH,
                   "Method %s arity error: %d instead of %d\nCulprit: 0x%p\n",
                   method->_content.asMethod.name, 
                   argc,
                   method->_content.asMethod.arity,
                   (void*) receiver);
          /*DEBUG*/
          fprintf(stderr, "%s", ILP_the_exception._content.asException.message);
          ILP_the_exception._content.asException.culprit[0] = receiver;
          ILP_the_exception._content.asException.culprit[1] = 
               (ILP_Object) method;
          ILP_the_exception._content.asException.culprit[2] = NULL;
          ILP_throw((ILP_Object) &ILP_the_exception);
          /* UNREACHED */
          return NULL;
     };
     {
          int index = method->_content.asMethod.index;
          return oclass->_content.asClass.method[index];
     }
}

ILP_Object 
ILP_find_and_call_super_method ( 
     ILP_Method current_method, 
     ILP_general_function super_method, 
     ILP_Object arguments[] )
{ 
     /* assert( super_method != NULL ); */ 
     ILP_Object self = arguments[0];
     int arity = current_method->_content.asMethod.arity;
     switch ( arity ) { 
          case 0: { 
               return (*super_method)(NULL, self); 
          } 
          case 1: { 
               return (*super_method)(NULL, self, arguments[1]); 
          } 
          case 2: { 
               return (*super_method)(NULL, self, arguments[1], arguments[2]); 
          } 
          case 3: { 
               return (*super_method)(NULL, self, arguments[1],  
                                            arguments[2],  
                                            arguments[3]); 
          } 
          default: { 
               snprintf(ILP_the_exception._content.asException.message, 
                        ILP_EXCEPTION_BUFFER_LENGTH, 
                        "Cannot invoke supermethod %snCulprit: 0x%pn", 
                        current_method->_content.asMethod.name,  
                        (void*) self ); 
               /*DEBUG*/ 
               fprintf(stderr, "%s", ILP_the_exception._content.asException.message); 
               ILP_the_exception._content.asException.culprit[0] = self; 
               ILP_the_exception._content.asException.culprit[1] =  
                    (ILP_Object) current_method; 
               ILP_the_exception._content.asException.culprit[2] = NULL; 
               ILP_throw((ILP_Object) &ILP_the_exception); 
               /* UNREACHED */ 
               return NULL; 
          } 
     } 
}

ILP_Object 
ILP_dont_call_super_method (
     ILP_Method current_method,
     ILP_general_function super_method,
     ILP_Object arguments[] )
{
     ILP_Object self = arguments[0];
     /* assert ( super_method == NULL ); */
     snprintf(ILP_the_exception._content.asException.message,
              ILP_EXCEPTION_BUFFER_LENGTH,
              "No supermethod %s\nCulprit: 0x%p\n",
              current_method->_content.asMethod.name, 
              (void*) self );
     /*DEBUG*/
     fprintf(stderr, "%s", ILP_the_exception._content.asException.message);
     ILP_the_exception._content.asException.culprit[0] = self;
     ILP_the_exception._content.asException.culprit[1] = 
          (ILP_Object) current_method;
     ILP_the_exception._content.asException.culprit[2] = NULL;
     ILP_throw((ILP_Object) &ILP_the_exception);
     /* UNREACHED */
     return NULL;
}

ILP_general_function
ILP_find_invokee (ILP_Object closure, int argc)
{
     if ( ! ILP_IsA(closure, Closure) ) {
          ILP_domain_error("Not a closure", (ILP_Object)closure);
     }
     if ( argc != closure->_content.asClosure.arity ) {
          snprintf(ILP_the_exception._content.asException.message,
                   ILP_EXCEPTION_BUFFER_LENGTH,
                   "Closure arity error: %d instead of %d\nCulprit: 0x%p\n",
                   argc,
                   closure->_content.asClosure.arity,
                   (void*) closure);
          /*DEBUG*/
          fprintf(stderr, "%s", ILP_the_exception._content.asException.message);
          ILP_the_exception._content.asException.culprit[0] = 
               (ILP_Object) closure;
          ILP_the_exception._content.asException.culprit[1] = NULL;
          ILP_throw((ILP_Object) &ILP_the_exception);
          /* UNREACHED */
          return NULL;
     }

     ILP_general_function f = closure->_content.asClosure.function;
     return f;
}

ILP_Object
ILP_make_closure(ILP_general_function f, int arity, int argc, ...)
{
     va_list args;
     int i;
     ILP_Object closure = ILP_AllocateClosure(arity);
     closure->_content.asClosure.function = f;
     closure->_content.asClosure.arity = arity;
     va_start(args, argc);
     for ( i=0 ; i<argc ; i++ ) {
          closure->_content.asClosure.closed_variables[i] = 
               va_arg(args, ILP_Object);
     }
     va_end(args);
     return closure;
}

ILP_Object
ILP_invoke (ILP_Object closure, int argc, ... )
{
     va_list args;
     ILP_Object result;
     ILP_general_function f = ILP_find_invokee(closure, argc);

     va_start(args, argc);
     // FIXME restriction on arity! Merge with supercaller!
     switch ( argc ) {
          case 0: {
               result = f(closure);
               break;
          }
          case 1: {
               ILP_Object arg1 = va_arg(args, ILP_Object);
               result = f(closure, arg1);
               break;
          }
          case 2: {
               ILP_Object arg1 = va_arg(args, ILP_Object);
               ILP_Object arg2 = va_arg(args, ILP_Object);
               result = f(closure, arg1, arg2);
               break;
          }
          case 3: {
               ILP_Object arg1 = va_arg(args, ILP_Object);
               ILP_Object arg2 = va_arg(args, ILP_Object);
               ILP_Object arg3 = va_arg(args, ILP_Object);
               result = f(closure, arg1, arg2, arg3);
               break;
          }
          case 4: {
               ILP_Object arg1 = va_arg(args, ILP_Object);
               ILP_Object arg2 = va_arg(args, ILP_Object);
               ILP_Object arg3 = va_arg(args, ILP_Object);
               ILP_Object arg4 = va_arg(args, ILP_Object);
               result = f(closure, arg1, arg2, arg3, arg4);
               break;
          }
          default: {
          }
     }
     va_end(args);
     return result;
}

/** Allocate a box with initial value o */

ILP_Object
ILP_make_box (ILP_Object o)
{
     ILP_Object box = ILP_AllocateBox();
     box->_content.asBox.value = o;
     return box;
}

/** Allocators. */

ILP_Object
ILP_malloc (int size, ILP_Class class)
{
     ILP_Object result = ILP_MALLOC(size);
     if ( result == NULL ) {
          return ILP_die("Memory exhaustion");
     };
     result->_class = class;
     return result;
}

ILP_Object
ILP_make_instance (ILP_Class class) 
{
     int size = sizeof(ILP_Class);
     size += sizeof(ILP_Object) * class->_content.asClass.fields_count;
     return ILP_malloc(size, class);
}

ILP_Object
ILP_make_boolean (int b)
{
     if ( b ) {
          return ILP_TRUE;
     } else {
          return ILP_FALSE;
     }
}

ILP_Object
ILP_make_integer (int d)
{
     ILP_Object result = ILP_AllocateInteger();
     result->_content.asInteger = d;
     return result;
}

ILP_Object
ILP_make_float (double d)
{
     ILP_Object result = ILP_AllocateFloat();
     result->_content.asFloat = d;
     return result;
}

ILP_Object
ILP_pi ()
{
     static ILP_Object object_pi = NULL;
     if ( object_pi == NULL ) {
          object_pi = ILP_make_float(ILP_PI_VALUE);
     }
     return object_pi;
}

ILP_Object
ILP_make_string (char *s)
{
     int size = strlen(s);
     ILP_Object result = ILP_AllocateString(size);
     result->_content.asString._size = size;
     memmove(result->_content.asString.asCharacter, s, size);
     return result;
}

/** String primitives */

static ILP_Object
ILP_concatenate_strings (ILP_Object o1, ILP_Object o2)
{
     int size1 = o1->_content.asString._size;
     int total_size = size1 + o2->_content.asString._size;
     ILP_Object result = ILP_AllocateString(total_size);
     memmove(&(result->_content.asString.asCharacter[0]), 
             o1->_content.asString.asCharacter,
             o1->_content.asString._size);
     memmove(&(result->_content.asString.asCharacter[size1]),
             o2->_content.asString.asCharacter,
             o2->_content.asString._size);
     return result;
}

/** Unary operators */

ILP_Object
ILP_make_opposite (ILP_Object o)
{
     if ( ILP_isInteger(o) ) {
          ILP_Object result = ILP_AllocateInteger();
          result->_content.asInteger = (- o->_content.asInteger);
          return result;
     } else if ( ILP_isFloat(o) ) {
          ILP_Object result = ILP_AllocateFloat();
          result->_content.asFloat = (- o->_content.asFloat);
          return result;
     } else {
         return ILP_domain_error("Not a number", o);
     }
}

ILP_Object
ILP_make_negation (ILP_Object o)
{
     ILP_CheckIfBoolean(o);
     {
          if ( ILP_isTrue(o) ) {
               return ILP_FALSE;
          } else {
               return ILP_TRUE;
          }
     }
}

/** Binary operators */

/* DefineOperator(addition, +) is incorrect since + may (as in javascript)
 * concatenate strings. */

ILP_Object
ILP_make_addition (ILP_Object o1, ILP_Object o2)
{
      if ( ILP_isString(o1) || ILP_isString(o2)) {
          ILP_Object v1 = ILP_isString(o1) ? o1 : ILP_to_string(o1);
          ILP_Object v2 = ILP_isString(o2) ? o2 : ILP_to_string(o2);
          return ILP_concatenate_strings(v1, v2);
     } else if ( ILP_isInteger(o1) ) {
          if ( ILP_isInteger(o2) ) {
               ILP_Object result = ILP_AllocateInteger();
               result->_content.asInteger =
                    o1->_content.asInteger + o2->_content.asInteger;
               return result;
          } else if ( ILP_isFloat(o2) ) {
               ILP_Object result = ILP_AllocateFloat();
               result->_content.asFloat =
                    o1->_content.asInteger + o2->_content.asFloat;
               return result;
          } else {
               return ILP_domain_error("Not a number", o2);
          }
     } else if ( ILP_isFloat(o1) ) {
          if ( ILP_isInteger(o2) ) {
               ILP_Object result = ILP_AllocateFloat();
               result->_content.asFloat =
                    o1->_content.asFloat + o2->_content.asInteger;
               return result;
          } else if ( ILP_isFloat(o2) ) {
               ILP_Object result = ILP_AllocateFloat();
               result->_content.asFloat =
                    o1->_content.asFloat + o2->_content.asFloat;
               return result;
          } else {
               return ILP_domain_error("Not a number", o2);
          }
     } else if ( ILP_isString(o1) || ILP_isString(o2)) {
          ILP_Object v1 = ILP_isString(o1) ? o1 : ILP_to_string(o1);
          ILP_Object v2 = ILP_isString(o2) ? o2 : ILP_to_string(o2);
          return ILP_concatenate_strings(v1, v2);
     } else {
          return ILP_domain_error("Not addable", o1);
     }
}

#define DefineOperator(name,op)                                        \
ILP_Object                                                             \
ILP_make_##name (ILP_Object o1, ILP_Object o2)                         \
{                                                                      \
     if ( ILP_isInteger(o1) ) {                                        \
          if ( ILP_isInteger(o2) ) {                                   \
               ILP_Object result = ILP_AllocateInteger();              \
               result->_content.asInteger =                            \
                    o1->_content.asInteger op o2->_content.asInteger;  \
               return result;                                          \
          } else if ( ILP_isFloat(o2) ) {                              \
               ILP_Object result = ILP_AllocateFloat();                \
               result->_content.asFloat =                              \
                    o1->_content.asInteger op o2->_content.asFloat;    \
               return result;                                          \
          } else {                                                     \
               return ILP_domain_error("Not a number", o2);            \
          }                                                            \
     } else if ( ILP_isFloat(o1) ) {                                   \
          if ( ILP_isInteger(o2) ) {                                   \
               ILP_Object result = ILP_AllocateFloat();                \
               result->_content.asFloat =                              \
                    o1->_content.asFloat op o2->_content.asInteger;    \
               return result;                                          \
          } else if ( ILP_isFloat(o2) ) {                              \
               ILP_Object result = ILP_AllocateFloat();                \
               result->_content.asFloat =                              \
                    o1->_content.asFloat op o2->_content.asFloat;      \
               return result;                                          \
          } else {                                                     \
               return ILP_domain_error("Not a number", o2);            \
          }                                                            \
     } else {                                                          \
          return ILP_domain_error("Not a number", o1);                 \
     }                                                                 \
}

DefineOperator(subtraction, -)
DefineOperator(multiplication, *)
DefineOperator(division, /)

/* DefineOperator(modulo, %) is incorrect since modulo works only on
 * integers. */

ILP_Object
ILP_make_modulo (ILP_Object o1, ILP_Object o2)
{
     if ( ILP_isInteger(o1) ) {
          if ( ILP_isInteger(o2) ) {
               ILP_Object result = ILP_AllocateInteger();
               result->_content.asInteger =
                    o1->_content.asInteger % o2->_content.asInteger;
               return result;
          } else {
               return ILP_domain_error("Not an integer", o2);
          }
     } else {
          return ILP_domain_error("Not an integer", o1);
     }
}

#define DefineComparator(name,op)                                      \
ILP_Object                                                             \
ILP_compare_##name (ILP_Object o1, ILP_Object o2)                      \
{                                                                      \
     if ( ILP_isInteger(o1) ) {                                        \
          if ( ILP_isInteger(o2) ) {                                   \
               return ILP_make_boolean(                                \
                    o1->_content.asInteger op o2->_content.asInteger); \
          } else if ( ILP_isFloat(o2) ) {                              \
               return ILP_make_boolean(                                \
                    o1->_content.asInteger op o2->_content.asFloat);   \
          } else {                                                     \
               return ILP_make_boolean(1 op 0);                        \
          }                                                            \
     } else if ( ILP_isFloat(o1) ) {                                   \
          if ( ILP_isInteger(o2) ) {                                   \
               return ILP_make_boolean(                                \
                    o1->_content.asFloat op o2->_content.asInteger);   \
          } else if ( ILP_isFloat(o2) ) {                              \
               return ILP_make_boolean(                                \
                    o1->_content.asFloat op o2->_content.asFloat);     \
          } else {                                                     \
               return ILP_make_boolean(1 op 0);                        \
          }                                                            \
     } else if ( ILP_isString(o1) && ILP_isString(o2) ) {              \
          return ILP_make_boolean(                                     \
                    strcmp(                                            \
                        o1->_content.asString.asCharacter,             \
                        o2->_content.asString.asCharacter ) op 0       \
                  );                                                   \
     } else if ( ILP_isBoolean(o1) && ILP_isBoolean(o2) ) {            \
          return ILP_make_boolean(                                     \
                        o1->_content.asBoolean op                      \
                        o2->_content.asBoolean                         \
                  );                                                   \
     } else {                                                          \
          return ILP_make_boolean(1 op 0);                             \
     }                                                                 \
}

DefineComparator(equal, ==)
DefineComparator(not_equal, !=)

#define DefineOrderComparator(name,op)                                 \
ILP_Object                                                             \
ILP_compare_##name (ILP_Object o1, ILP_Object o2)                      \
{                                                                      \
     if ( ILP_isInteger(o1) ) {                                        \
          if ( ILP_isInteger(o2) ) {                                   \
               return ILP_make_boolean(                                \
                    o1->_content.asInteger op o2->_content.asInteger); \
          } else if ( ILP_isFloat(o2) ) {                              \
               return ILP_make_boolean(                                \
                    o1->_content.asInteger op o2->_content.asFloat);   \
          } else {                                                     \
               return ILP_domain_error("Not a number", o2);            \
          }                                                            \
     } else if ( ILP_isFloat(o1) ) {                                   \
          if ( ILP_isInteger(o2) ) {                                   \
               return ILP_make_boolean(                                \
                    o1->_content.asFloat op o2->_content.asInteger);   \
          } else if ( ILP_isFloat(o2) ) {                              \
               return ILP_make_boolean(                                \
                    o1->_content.asFloat op o2->_content.asFloat);     \
          } else {                                                     \
               return ILP_domain_error("Not a number", o2);            \
          }                                                            \
     } else if ( ILP_isString(o1) && ILP_isString(o2) ) {              \
          return ILP_make_boolean(                                     \
                    strcmp(                                            \
                        o1->_content.asString.asCharacter,             \
                        o2->_content.asString.asCharacter ) op 0       \
                  );                                                   \
     } else {                                                          \
          return ILP_domain_error("Non comparable", o1);               \
     }                                                                 \
}

DefineOrderComparator(less_than, <)
DefineOrderComparator(less_than_or_equal, <=)
DefineOrderComparator(greater_than, >)
DefineOrderComparator(greater_than_or_equal, >=)

ILP_Object
ILP_and (ILP_Object o1, ILP_Object o2)
{
     if ( ILP_isEquivalentToTrue(o1) ) {
          if ( ILP_isEquivalentToTrue(o2) ) {
               return o2;
          }
     }
     return ILP_FALSE;
}

ILP_Object
ILP_or (ILP_Object o1, ILP_Object o2)
{
     if ( ILP_isEquivalentToTrue(o1) ) {
          return o1;
     }
     if ( ILP_isEquivalentToTrue(o2) ) {
          return o2;
     }
     return ILP_FALSE;
}

ILP_Object
ILP_xor (ILP_Object o1, ILP_Object o2)
{
     if ( ILP_isEquivalentToTrue(o1) ) {
          if ( ILP_isEquivalentToTrue(o2) ) {
               return ILP_FALSE;
          } else {
               return o1;
          }
     } else {
          if ( ILP_isEquivalentToTrue(o2) ) {
               return o2;
          } else {
               return ILP_FALSE;
          }
     }
}

/** Primitives */

ILP_Object
ILP_newline () 
{
     fputc('\n', stdout);
     return ILP_FALSE;
}

/** Print an instance */

void
ILP_print_fields (ILP_Object o,
                  ILP_Field last)
{
     if ( last == NULL ) {
          return;
     };
     ILP_print_fields(o, last->_content.asField.previous_field);
     fprintf(stdout, ":%s=", last->_content.asField.name);
     ILP_print(o->_content.asInstance.field[last->_content.asField.offset]);
}

ILP_Object
ILP_print (ILP_Object self)
{
     if ( self->_class == &ILP_object_Integer_class ) {
          fprintf(stdout, "%d", self->_content.asInteger);
     } else if ( self->_class == &ILP_object_Float_class ) {
          fprintf(stdout, "%.5g", self->_content.asFloat);
     } else if ( self->_class == &ILP_object_Boolean_class ) {
          fprintf(stdout, "%s", (ILP_isTrue(self) ? "true" : "false"));
     } else if ( self->_class == &ILP_object_String_class ) {
          fprintf(stdout, "%s", self->_content.asString.asCharacter);
     } else if ( self->_class == &ILP_object_Class_class ) {
          fprintf(stdout, "<Class:%s>", self->_content.asClass.name);
     } else if ( self->_class == &ILP_object_Method_class ) {
          fprintf(stdout, "<Method:%s>", self->_content.asMethod.name);
     } else if ( self->_class == &ILP_object_Field_class ) {
          fprintf(stdout, "<Field:%s>", self->_content.asField.name);
     } else {
          fprintf(stdout, "<%s", self->_class->_content.asClass.name);
          ILP_print_fields(self, self->_class->_content.asClass.last_field);
          fprintf(stdout, ">");
     }
     return ILP_FALSE;
}

ILP_Object
ILPm_print (ILP_Closure useless, ILP_Object self)
{
     return ILP_print(self);
}

ILP_Object
ILP_to_string (ILP_Object self)
{
     if ( self->_class == &ILP_object_Integer_class ) {
          char buf[256];
          snprintf(buf, sizeof(buf), "%d", self->_content.asInteger);
          return ILP_make_string(buf);
     } else if ( self->_class == &ILP_object_Float_class ) {
          char buf[256];
          snprintf(buf, sizeof(buf), "%.5g", self->_content.asFloat);
          return ILP_make_string(buf);
     } else if ( self->_class == &ILP_object_Boolean_class ) {
          return ILP_make_string(ILP_isTrue(self) ? "true" : "false");
     } else if ( self->_class == &ILP_object_String_class ) {
          return self;
     } else if ( self->_class == &ILP_object_Class_class ) {
          return ILP_make_string(self->_content.asClass.name);
     } else if ( self->_class == &ILP_object_Method_class ) {
          return ILP_make_string(self->_content.asMethod.name);
     } else if ( self->_class == &ILP_object_Field_class ) {
          return ILP_make_string(self->_content.asField.name);
     } else {
          return ILP_make_string("<unimplemented>");
     }
}

/** Type name of a value (as a string). */
ILP_Object
ILP_type_of (ILP_Object self)
{
     return ILP_make_string(self->_class->_content.asClass.name);
}

/** Yields the class of an object. The class is also an object
 * as advocated by ObjVlisp. */

ILP_Object
ILP_classOf (ILP_Object self) 
{
     return (ILP_Object) (self->_class);
}

ILP_Object
ILPm_classOf (ILP_Closure useless, ILP_Object self)
{
     return (ILP_Object) (self->_class);
}

/* end of ilpObj.c */
