package com.algocompiler

sealed class ASTNode

data class Program(
        val name: String,
        val variables: List<VariableDeclaration>,
        val constants: List<ConstantDeclaration>,
        val functions: List<FunctionDeclaration>,
        val statements: List<Statement>
) : ASTNode()

data class VariableDeclaration(
    val name: String,
    val type: String,
    val arraySize: Int? = null,
    val arraySize2: Int? = null,
    val initialValue: Expression? = null
) : ASTNode()

data class ConstantDeclaration(val name: String, val type: String, val value: Expression) :
        ASTNode()

data class FunctionDeclaration(
        val name: String,
        val parameters: List<VariableDeclaration>,
        val returnType: String?,
        val variables: List<VariableDeclaration>,
        val body: List<Statement>
) : ASTNode()

sealed class Statement : ASTNode()

data class Assignment(val variable: String, val expression: Expression) : Statement()

data class ArrayAssignment(
        val arrayName: String,
        val index: Expression,
        val expression: Expression
) : Statement()

data class MatrixAssignment(
        val matrixName: String,
        val index1: Expression,
        val index2: Expression,
        val expression: Expression
) : Statement()

data class ElseIfClause(
        val condition: Expression,
        val thenBranch: List<Statement>
) : ASTNode()

data class IfStatement(
        val condition: Expression,
        val thenBranch: List<Statement>,
        val elseIfClauses: List<ElseIfClause> = emptyList(),
        val elseBranch: List<Statement>?
) : Statement()

data class ForLoop(
        val variable: String,
        val start: Expression,
        val end: Expression,
        val body: List<Statement>
) : Statement()

data class WhileLoop(val condition: Expression, val body: List<Statement>) : Statement()

data class RepeatUntilLoop(val body: List<Statement>, val condition: Expression) : Statement()

data class WhenStatement(
        val expression: Expression,
        val cases: List<WhenCase>,
        val defaultCase: List<Statement>?
) : Statement()

data class WhenCase(val value: Expression, val statements: List<Statement>) : ASTNode()

data class WriteStatement(val expressions: List<Expression>, val newline: Boolean = true) :
        Statement()

sealed class ReadTarget : ASTNode()

data class SimpleReadTarget(val name: String) : ReadTarget()

data class ArrayReadTarget(val arrayName: String, val index: Expression) : ReadTarget()

data class ReadStatement(val targets: List<ReadTarget>) : Statement()

data class ReturnStatement(val expression: Expression?) : Statement()

data class FunctionCall(val name: String, val arguments: List<Expression>) : Statement()

sealed class Expression : ASTNode()

data class NumberLiteral(val value: Double) : Expression()

data class StringLiteral(val value: String) : Expression()

data class CharLiteral(val value: Char) : Expression()

data class BooleanLiteral(val value: Boolean) : Expression()

data class Variable(val name: String) : Expression()

data class ArrayAccess(val name: String, val index: Expression) : Expression()

data class MatrixAccess(val name: String, val index1: Expression, val index2: Expression) : Expression()

data class FunctionCallExpression(val name: String, val arguments: List<Expression>) : Expression()

data class BinaryOp(val left: Expression, val operator: String, val right: Expression) :
        Expression()

data class UnaryOp(val operator: String, val operand: Expression) : Expression()

data class ConditionalExpression(
        val condition: Expression,
        val thenValue: Expression,
        val elseValue: Expression
) : Expression()

data class ArrayLiteral(val elements: List<Expression>) : Expression()
