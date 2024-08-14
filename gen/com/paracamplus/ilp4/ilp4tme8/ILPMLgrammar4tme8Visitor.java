// Generated from /home/weida/git/ilp4/Java/src/com/paracamplus/ilp4/ilp4tme8/ILPMLgrammar4tme8.g4 by ANTLR 4.13.1
package com.paracamplus.ilp4.ilp4tme8;

    package antlr4;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ILPMLgrammar4tme8Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ILPMLgrammar4tme8Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ILPMLgrammar4tme8Parser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(ILPMLgrammar4tme8Parser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GlobalFunctionDefinition}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#globalDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalFunctionDefinition(ILPMLgrammar4tme8Parser.GlobalFunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ClassDefinition}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#globalDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDefinition(ILPMLgrammar4tme8Parser.ClassDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ILPMLgrammar4tme8Parser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(ILPMLgrammar4tme8Parser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ILPMLgrammar4tme8Parser#methodDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDef(ILPMLgrammar4tme8Parser.MethodDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ILPMLgrammar4tme8Parser#globalFunDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalFunDef(ILPMLgrammar4tme8Parser.GlobalFunDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link ILPMLgrammar4tme8Parser#localFunDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalFunDef(ILPMLgrammar4tme8Parser.LocalFunDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code New}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNew(ILPMLgrammar4tme8Parser.NewContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(ILPMLgrammar4tme8Parser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Invocation}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvocation(ILPMLgrammar4tme8Parser.InvocationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstFloat}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstFloat(ILPMLgrammar4tme8Parser.ConstFloatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Self}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelf(ILPMLgrammar4tme8Parser.SelfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableAssign}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAssign(ILPMLgrammar4tme8Parser.VariableAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReadField}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadField(ILPMLgrammar4tme8Parser.ReadFieldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Send}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSend(ILPMLgrammar4tme8Parser.SendContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstInteger}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstInteger(ILPMLgrammar4tme8Parser.ConstIntegerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Super}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuper(ILPMLgrammar4tme8Parser.SuperContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Try}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTry(ILPMLgrammar4tme8Parser.TryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Codefinitions}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCodefinitions(ILPMLgrammar4tme8Parser.CodefinitionsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Lambda}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambda(ILPMLgrammar4tme8Parser.LambdaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Binding}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinding(ILPMLgrammar4tme8Parser.BindingContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Loop}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop(ILPMLgrammar4tme8Parser.LoopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Alternative}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlternative(ILPMLgrammar4tme8Parser.AlternativeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code HasProperty}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHasProperty(ILPMLgrammar4tme8Parser.HasPropertyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Sequence}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSequence(ILPMLgrammar4tme8Parser.SequenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WriteProperty}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteProperty(ILPMLgrammar4tme8Parser.WritePropertyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstFalse}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstFalse(ILPMLgrammar4tme8Parser.ConstFalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Unary}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary(ILPMLgrammar4tme8Parser.UnaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstTrue}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstTrue(ILPMLgrammar4tme8Parser.ConstTrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReadProperty}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadProperty(ILPMLgrammar4tme8Parser.ReadPropertyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WriteField}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteField(ILPMLgrammar4tme8Parser.WriteFieldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstString}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstString(ILPMLgrammar4tme8Parser.ConstStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Binary}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinary(ILPMLgrammar4tme8Parser.BinaryContext ctx);
}