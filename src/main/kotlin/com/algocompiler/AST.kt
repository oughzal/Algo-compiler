package com.algocompiler

sealed class ASTNode(open val line: Int = 0)

data class Program(
        val name: String,
        val variables: List<VariableDeclaration>,
        val constants: List<ConstantDeclaration>,
        val functions: List<FunctionDeclaration>,
        val statements: List<Statement>,
        override val line: Int = 0
) : ASTNode(line)

data class VariableDeclaration(
    val name: String,
    val type: String,
    val arraySize: Int? = null,
    val arraySize2: Int? = null,
    val initialValue: Expression? = null,
    override val line: Int = 0
) : ASTNode(line)

data class ConstantDeclaration(val name: String, val type: String, val value: Expression, override val line: Int = 0) :
        ASTNode(line)

data class FunctionDeclaration(
        val name: String,
        val parameters: List<VariableDeclaration>,
        val returnType: String?,
        val variables: List<VariableDeclaration>,
        val body: List<Statement>,
        override val line: Int = 0
) : ASTNode(line)

sealed class Statement(override val line: Int = 0) : ASTNode(line)

data class Assignment(val variable: String, val expression: Expression, override val line: Int = 0) : Statement(line)

data class ArrayAssignment(
        val arrayName: String,
        val index: Expression,
        val expression: Expression,
        override val line: Int = 0
) : Statement(line)

data class MatrixAssignment(
        val matrixName: String,
        val index1: Expression,
        val index2: Expression,
        val expression: Expression,
        override val line: Int = 0
) : Statement(line)

data class ElseIfClause(
        val condition: Expression,
        val thenBranch: List<Statement>,
        override val line: Int = 0
) : ASTNode(line)

data class IfStatement(
        val condition: Expression,
        val thenBranch: List<Statement>,
        val elseIfClauses: List<ElseIfClause> = emptyList(),
        val elseBranch: List<Statement>?,
        override val line: Int = 0
) : Statement(line)

data class ForLoop(
        val variable: String,
        val start: Expression,
        val end: Expression,
        val step: Expression? = null,
        val body: List<Statement>,
        override val line: Int = 0
) : Statement(line)

data class WhileLoop(val condition: Expression, val body: List<Statement>, override val line: Int = 0) : Statement(line)

data class RepeatUntilLoop(val body: List<Statement>, val condition: Expression, override val line: Int = 0) : Statement(line)

data class WhenStatement(
        val expression: Expression,
        val cases: List<WhenCase>,
        val defaultCase: List<Statement>?,
        override val line: Int = 0
) : Statement(line)

data class WhenCase(val value: Expression, val statements: List<Statement>, override val line: Int = 0) : ASTNode(line)

data class WriteStatement(val expressions: List<Expression>, val newline: Boolean = true, override val line: Int = 0) :
        Statement(line)

sealed class ReadTarget(override val line: Int = 0) : ASTNode(line)

data class SimpleReadTarget(val name: String, override val line: Int = 0) : ReadTarget(line)

data class ArrayReadTarget(val arrayName: String, val index: Expression, override val line: Int = 0) : ReadTarget(line)

data class ReadStatement(val targets: List<ReadTarget>, override val line: Int = 0) : Statement(line)

data class ReturnStatement(val expression: Expression?, override val line: Int = 0) : Statement(line)

data class FunctionCall(val name: String, val arguments: List<Expression>, override val line: Int = 0) : Statement(line)

sealed class Expression(override val line: Int = 0) : ASTNode(line)

data class NumberLiteral(val value: Double, override val line: Int = 0) : Expression(line)

data class StringLiteral(val value: String, override val line: Int = 0) : Expression(line)

data class CharLiteral(val value: Char, override val line: Int = 0) : Expression(line)

data class BooleanLiteral(val value: Boolean, override val line: Int = 0) : Expression(line)

data class Variable(val name: String, override val line: Int = 0) : Expression(line)

data class ArrayAccess(val name: String, val index: Expression, override val line: Int = 0) : Expression(line)

data class MatrixAccess(val name: String, val index1: Expression, val index2: Expression, override val line: Int = 0) : Expression(line)

data class FunctionCallExpression(val name: String, val arguments: List<Expression>, override val line: Int = 0) : Expression(line)

data class BinaryOp(val left: Expression, val operator: String, val right: Expression, override val line: Int = 0) :
        Expression(line)

data class UnaryOp(val operator: String, val operand: Expression, override val line: Int = 0) : Expression(line)

data class ConditionalExpression(
        val condition: Expression,
        val thenValue: Expression,
        val elseValue: Expression,
        override val line: Int = 0
) : Expression(line)

data class ArrayLiteral(val elements: List<Expression>, override val line: Int = 0) : Expression(line)
