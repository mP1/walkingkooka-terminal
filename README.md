# walkingkooka-terminal

Defines some minimal types relating to a text-based terminal session. This is intended to define the basic I/O
operations,
and not the semantics of how input text is interpreted, processed or ignored. However following the basic ideas of a
unix shell, environment variables, will hold various important variables which are updated as necessary to remember some
state.

- A separate project supports SSH connections [TODO].

### [Functions](https://github.com/mP1/walkingkooka-tree/blob/master/src/main/java/walkingkooka/tree/expression/function/ExpressionFunction.java)

Functions that will be useful within a terminal session

- [print](https://github.com/mP1/walkingkooka-terminal/tree/master/src/main/java/walkingkooka/terminal/expression/function/TerminalExpressionFunctionPrint.java)
- [println](https://github.com/mP1/walkingkooka-terminal/tree/master/src/main/java/walkingkooka/terminal/expression/function/TerminalExpressionFunctionPrintln.java)
- [quit](https://github.com/mP1/walkingkooka-terminal/tree/master/src/main/java/walkingkooka/terminal/expression/function/TerminalExpressionFunctionQuit.java)
- [readLine](https://github.com/mP1/walkingkooka-terminal/tree/master/src/main/java/walkingkooka/terminal/expression/function/TerminalExpressionFunctionReadLine.java)
