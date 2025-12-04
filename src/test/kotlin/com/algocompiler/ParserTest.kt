package com.algocompiler

import kotlin.test.*

class ParserTest {

    @Test
    fun testSimpleProgram() {
        val code = """
            algorithme Test
            debut
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals("Test", program.name)
        assertEquals(0, program.variables.size)
        assertEquals(0, program.statements.size)
    }

    @Test
    fun testVariableDeclaration() {
        val code = """
            algorithme Test
            variables
                x, y : entier
                nom : chaine
                c : caractere
            debut
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(4, program.variables.size)
        assertEquals("x", program.variables[0].name)
        assertEquals("entier", program.variables[0].type)
        assertEquals("y", program.variables[1].name)
        assertEquals("entier", program.variables[1].type)
        assertEquals("nom", program.variables[2].name)
        assertEquals("chaine", program.variables[2].type)
        assertEquals("c", program.variables[3].name)
        assertEquals("caractere", program.variables[3].type)
    }

    @Test
    fun testConstantDeclaration() {
        val code = """
            algorithme Test
            constantes
                PI : reel = 3.14
                MAX : entier = 100
            debut
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(2, program.constants.size)
        assertEquals("PI", program.constants[0].name)
        assertEquals("reel", program.constants[0].type)
        assertEquals("MAX", program.constants[1].name)
        assertEquals("entier", program.constants[1].type)
    }

    @Test
    fun testAssignment() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                x = 5
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(1, program.statements.size)
        assertTrue(program.statements[0] is Assignment)
        val assignment = program.statements[0] as Assignment
        assertEquals("x", assignment.variable)
        assertTrue(assignment.expression is NumberLiteral)
    }

    @Test
    fun testCharacterAssignment() {
        val code = """
            algorithme Test
            variables
                c : caractere
            debut
                c = 'A'
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(1, program.statements.size)
        assertTrue(program.statements[0] is Assignment)
        val assignment = program.statements[0] as Assignment
        assertEquals("c", assignment.variable)
        assertTrue(assignment.expression is CharLiteral)
        assertEquals('A', (assignment.expression as CharLiteral).value)
    }

    @Test
    fun testIfStatement() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                si x > 0 alors
                    x = 1
                finsi
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(1, program.statements.size)
        assertTrue(program.statements[0] is IfStatement)
        val ifStmt = program.statements[0] as IfStatement
        assertTrue(ifStmt.condition is BinaryOp)
        assertEquals(1, ifStmt.thenBranch.size)
        assertNull(ifStmt.elseBranch)
    }

    @Test
    fun testIfElseStatement() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                si x > 0 alors
                    x = 1
                sinon
                    x = 0
                finsi
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertTrue(program.statements[0] is IfStatement)
        val ifStmt = program.statements[0] as IfStatement
        assertNotNull(ifStmt.elseBranch)
        assertEquals(1, ifStmt.elseBranch!!.size)
    }

    @Test
    fun testForLoop() {
        val code = """
            algorithme Test
            variables
                i : entier
            debut
                pour i de 1 à 10 faire
                    ecrire(i)
                finpour
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(1, program.statements.size)
        assertTrue(program.statements[0] is ForLoop)
        val forLoop = program.statements[0] as ForLoop
        assertEquals("i", forLoop.variable)
        assertTrue(forLoop.start is NumberLiteral)
        assertTrue(forLoop.end is NumberLiteral)
        assertEquals(1, forLoop.body.size)
    }

    @Test
    fun testWhileLoop() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                tantque x < 10 faire
                    x = x + 1
                fintantque
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(1, program.statements.size)
        assertTrue(program.statements[0] is WhileLoop)
        val whileLoop = program.statements[0] as WhileLoop
        assertTrue(whileLoop.condition is BinaryOp)
        assertEquals(1, whileLoop.body.size)
    }

    @Test
    fun testRepeatUntilLoop() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                repeter
                    x = x + 1
                jusqua x > 10
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(1, program.statements.size)
        assertTrue(program.statements[0] is RepeatUntilLoop)
        val repeatLoop = program.statements[0] as RepeatUntilLoop
        assertTrue(repeatLoop.condition is BinaryOp)
        assertEquals(1, repeatLoop.body.size)
    }

    @Test
    fun testWhenStatement() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                selon(x)
                    cas 1 : ecrire("un")
                    cas 2 : ecrire("deux")
                    defaut : ecrire("autre")
                finselon
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(1, program.statements.size)
        assertTrue(program.statements[0] is WhenStatement)
        val whenStmt = program.statements[0] as WhenStatement
        assertEquals(2, whenStmt.cases.size)
        assertNotNull(whenStmt.defaultCase)
    }

    @Test
    fun testArrayDeclaration() {
        val code = """
            algorithme Test
            variables
                tab : tableau[10] de entier
            debut
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(1, program.variables.size)
        assertEquals("tab", program.variables[0].name)
        assertEquals(10, program.variables[0].arraySize)
    }

    @Test
    fun testArrayAccess() {
        val code = """
            algorithme Test
            variables
                tab : tableau[10] de entier
                x : entier
            debut
                x = tab[5]
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertTrue(program.statements[0] is Assignment)
        val assignment = program.statements[0] as Assignment
        assertTrue(assignment.expression is ArrayAccess)
    }

    @Test
    fun testArrayAssignment() {
        val code = """
            algorithme Test
            variables
                tab : tableau[10] de entier
            debut
                tab[5] = 42
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertTrue(program.statements[0] is ArrayAssignment)
        val arrayAssign = program.statements[0] as ArrayAssignment
        assertEquals("tab", arrayAssign.arrayName)
    }

    @Test
    fun testFunctionDeclaration() {
        val code = """
            algorithme Test
            fonction somme(a : entier, b : entier) : entier
            debut
                retourner a + b
            fin
            debut
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(1, program.functions.size)
        assertEquals("somme", program.functions[0].name)
        assertEquals(2, program.functions[0].parameters.size)
        assertEquals("entier", program.functions[0].returnType)
    }

    @Test
    fun testProcedureDeclaration() {
        val code = """
            algorithme Test
            procedure afficher(msg : chaine)
            debut
                ecrire(msg)
            fin
            debut
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(1, program.functions.size)
        assertEquals("afficher", program.functions[0].name)
        assertNull(program.functions[0].returnType)
    }

    @Test
    fun testFunctionCall() {
        val code = """
            algorithme Test
            debut
                ecrire("Hello")
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals(1, program.statements.size)
        assertTrue(program.statements[0] is WriteStatement)
    }

    @Test
    fun testBinaryOperators() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                x = 1 + 2 * 3
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertTrue(program.statements[0] is Assignment)
        val assignment = program.statements[0] as Assignment
        assertTrue(assignment.expression is BinaryOp)
    }

    @Test
    fun testComparisonOperators() {
        val code = """
            algorithme Test
            variables
                result : booleen
            debut
                result = 5 == 5
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertTrue(program.statements[0] is Assignment)
        val assignment = program.statements[0] as Assignment
        assertTrue(assignment.expression is BinaryOp)
        assertEquals("==", (assignment.expression as BinaryOp).operator)
    }
}

