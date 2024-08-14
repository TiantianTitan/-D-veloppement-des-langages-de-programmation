// Generated from /home/weida/git/ilp4/Java/src/com/paracamplus/ilp4/ilp4tme8/ILPMLgrammar4tme8.g4 by ANTLR 4.13.1
package com.paracamplus.ilp4.ilp4tme8;

    package antlr4;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ILPMLgrammar4tme8Parser}.
 */
public interface ILPMLgrammar4tme8Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ILPMLgrammar4tme8Parser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(ILPMLgrammar4tme8Parser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link ILPMLgrammar4tme8Parser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(ILPMLgrammar4tme8Parser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GlobalFunctionDefinition}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#globalDef}.
	 * @param ctx the parse tree
	 */
	void enterGlobalFunctionDefinition(ILPMLgrammar4tme8Parser.GlobalFunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GlobalFunctionDefinition}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#globalDef}.
	 * @param ctx the parse tree
	 */
	void exitGlobalFunctionDefinition(ILPMLgrammar4tme8Parser.GlobalFunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ClassDefinition}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#globalDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDefinition(ILPMLgrammar4tme8Parser.ClassDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClassDefinition}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#globalDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDefinition(ILPMLgrammar4tme8Parser.ClassDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ILPMLgrammar4tme8Parser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(ILPMLgrammar4tme8Parser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link ILPMLgrammar4tme8Parser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(ILPMLgrammar4tme8Parser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link ILPMLgrammar4tme8Parser#methodDef}.
	 * @param ctx the parse tree
	 */
	void enterMethodDef(ILPMLgrammar4tme8Parser.MethodDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link ILPMLgrammar4tme8Parser#methodDef}.
	 * @param ctx the parse tree
	 */
	void exitMethodDef(ILPMLgrammar4tme8Parser.MethodDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link ILPMLgrammar4tme8Parser#globalFunDef}.
	 * @param ctx the parse tree
	 */
	void enterGlobalFunDef(ILPMLgrammar4tme8Parser.GlobalFunDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link ILPMLgrammar4tme8Parser#globalFunDef}.
	 * @param ctx the parse tree
	 */
	void exitGlobalFunDef(ILPMLgrammar4tme8Parser.GlobalFunDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link ILPMLgrammar4tme8Parser#localFunDef}.
	 * @param ctx the parse tree
	 */
	void enterLocalFunDef(ILPMLgrammar4tme8Parser.LocalFunDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link ILPMLgrammar4tme8Parser#localFunDef}.
	 * @param ctx the parse tree
	 */
	void exitLocalFunDef(ILPMLgrammar4tme8Parser.LocalFunDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code New}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNew(ILPMLgrammar4tme8Parser.NewContext ctx);
	/**
	 * Exit a parse tree produced by the {@code New}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNew(ILPMLgrammar4tme8Parser.NewContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVariable(ILPMLgrammar4tme8Parser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVariable(ILPMLgrammar4tme8Parser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Invocation}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInvocation(ILPMLgrammar4tme8Parser.InvocationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Invocation}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInvocation(ILPMLgrammar4tme8Parser.InvocationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConstFloat}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConstFloat(ILPMLgrammar4tme8Parser.ConstFloatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConstFloat}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitConstFloat(ILPMLgrammar4tme8Parser.ConstFloatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Self}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSelf(ILPMLgrammar4tme8Parser.SelfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Self}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSelf(ILPMLgrammar4tme8Parser.SelfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableAssign}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVariableAssign(ILPMLgrammar4tme8Parser.VariableAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableAssign}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVariableAssign(ILPMLgrammar4tme8Parser.VariableAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReadField}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterReadField(ILPMLgrammar4tme8Parser.ReadFieldContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReadField}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitReadField(ILPMLgrammar4tme8Parser.ReadFieldContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Send}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSend(ILPMLgrammar4tme8Parser.SendContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Send}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSend(ILPMLgrammar4tme8Parser.SendContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConstInteger}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConstInteger(ILPMLgrammar4tme8Parser.ConstIntegerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConstInteger}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitConstInteger(ILPMLgrammar4tme8Parser.ConstIntegerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Super}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSuper(ILPMLgrammar4tme8Parser.SuperContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Super}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSuper(ILPMLgrammar4tme8Parser.SuperContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Try}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTry(ILPMLgrammar4tme8Parser.TryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Try}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTry(ILPMLgrammar4tme8Parser.TryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Codefinitions}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCodefinitions(ILPMLgrammar4tme8Parser.CodefinitionsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Codefinitions}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCodefinitions(ILPMLgrammar4tme8Parser.CodefinitionsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Lambda}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLambda(ILPMLgrammar4tme8Parser.LambdaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Lambda}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLambda(ILPMLgrammar4tme8Parser.LambdaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Binding}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinding(ILPMLgrammar4tme8Parser.BindingContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Binding}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinding(ILPMLgrammar4tme8Parser.BindingContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Loop}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLoop(ILPMLgrammar4tme8Parser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Loop}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLoop(ILPMLgrammar4tme8Parser.LoopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Alternative}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAlternative(ILPMLgrammar4tme8Parser.AlternativeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Alternative}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAlternative(ILPMLgrammar4tme8Parser.AlternativeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code HasProperty}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterHasProperty(ILPMLgrammar4tme8Parser.HasPropertyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code HasProperty}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitHasProperty(ILPMLgrammar4tme8Parser.HasPropertyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Sequence}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSequence(ILPMLgrammar4tme8Parser.SequenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Sequence}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSequence(ILPMLgrammar4tme8Parser.SequenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WriteProperty}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterWriteProperty(ILPMLgrammar4tme8Parser.WritePropertyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WriteProperty}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitWriteProperty(ILPMLgrammar4tme8Parser.WritePropertyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConstFalse}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConstFalse(ILPMLgrammar4tme8Parser.ConstFalseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConstFalse}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitConstFalse(ILPMLgrammar4tme8Parser.ConstFalseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Unary}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnary(ILPMLgrammar4tme8Parser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Unary}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnary(ILPMLgrammar4tme8Parser.UnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConstTrue}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConstTrue(ILPMLgrammar4tme8Parser.ConstTrueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConstTrue}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitConstTrue(ILPMLgrammar4tme8Parser.ConstTrueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReadProperty}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterReadProperty(ILPMLgrammar4tme8Parser.ReadPropertyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReadProperty}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitReadProperty(ILPMLgrammar4tme8Parser.ReadPropertyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WriteField}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterWriteField(ILPMLgrammar4tme8Parser.WriteFieldContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WriteField}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitWriteField(ILPMLgrammar4tme8Parser.WriteFieldContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConstString}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConstString(ILPMLgrammar4tme8Parser.ConstStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConstString}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitConstString(ILPMLgrammar4tme8Parser.ConstStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Binary}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinary(ILPMLgrammar4tme8Parser.BinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Binary}
	 * labeled alternative in {@link ILPMLgrammar4tme8Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinary(ILPMLgrammar4tme8Parser.BinaryContext ctx);
}