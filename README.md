[![Build Status](https://github.com/mP1/walkingkooka-terminal/actions/workflows/build.yaml/badge.svg)](https://github.com/mP1/walkingkooka-terminal/actions/workflows/build.yaml/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/mP1/walkingkooka-terminal/badge.svg)](https://coveralls.io/github/mP1/walkingkooka-terminal)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/mP1/walkingkooka-terminal.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-terminal/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/mP1/walkingkooka-terminal.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-terminal/alerts/)
![](https://tokei.rs/b1/github/mP1/walkingkooka-terminal)
[![J2CL compatible](https://img.shields.io/badge/J2CL-compatible-brightgreen.svg)](https://github.com/mP1/j2cl-central)

# walkingkooka-terminal

Defines some minimal types relating to a text-based terminal session. This is intended to define the basic I/O
operations,
and not the semantics of how input text is interpreted, processed or ignored. However following the basic ideas of a
unix shell, environment variables, will hold various important variables which are updated as necessary to remember some
state.

- A separate project supports SSH
  connections [walkingkooka-terminal-apachesshd](https://github.com/mP1/walkingkooka-terminal-apachesshd).

### [Functions](https://github.com/mP1/walkingkooka-tree/blob/master/src/main/java/walkingkooka/tree/expression/function/ExpressionFunction.java)

Functions that will be useful within a terminal session

- [exit](https://github.com/mP1/walkingkooka-terminal/tree/master/src/main/java/walkingkooka/terminal/expression/function/TerminalExpressionFunctionExit.java)
- [print](https://github.com/mP1/walkingkooka-terminal/tree/master/src/main/java/walkingkooka/terminal/expression/function/TerminalExpressionFunctionPrint.java)
- [printEnv](https://github.com/mP1/walkingkooka-terminal/tree/master/src/main/java/walkingkooka/terminal/expression/function/TerminalExpressionFunctionPrintEnv.java)
- [println](https://github.com/mP1/walkingkooka-terminal/tree/master/src/main/java/walkingkooka/terminal/expression/function/TerminalExpressionFunctionPrintln.java)
- [readLine](https://github.com/mP1/walkingkooka-terminal/tree/master/src/main/java/walkingkooka/terminal/expression/function/TerminalExpressionFunctionReadLine.java)
- [shell](https://github.com/mP1/walkingkooka-terminal/tree/master/src/main/java/walkingkooka/terminal/expression/function/TerminalExpressionFunctionShell.java)
