# Spring-Bean-Generator
This cli tool generates basics Spring beans. Controllers, services and/or repositories to avoid boilerplate. It's basically like angular cli or rails cli.

How it works:
For now it only generates a java class at the location of the program. It is ready to be used as a basic jar as the first release is available. Just execute it as a basic jar and the help command will pop up by default.

for help -h

Understanding the code:
If you want to understand how it works I recommend being familiar with picocli as it does most of the heavy lifting in terms of parsing. There is a hidden topcommand that is just there to be able to have subcommands. Each run method are the commands logic that I have seperated into pipeline methods. It's ugly I know but I haven't implemented pipelines yet.
