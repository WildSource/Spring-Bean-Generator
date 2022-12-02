# Spring-Bean-Generator
This cli tool generates basics Spring beans. Controllers, services and/or repositories to avoid boilerplate. It's basically like angular cli or rails cli.

How it works:
For now it only generates a java class at the location of the program. It is not ready to be used as a jar yet because you can't choose what you generate. The only way to choose is to change what you feed to the CommandLine class in the main method and execute it. Although most options are implemented are working they are not optimized.

for help -h

Understanding the code:
If you want to understand how it works I recommend being familiar with picocli as it does most of the heavy lifting in terms of parsing. Each run method are the commands logic that I have seperated into pipeline methods. It's ugly I know but I haven't implemented pipelines yet.
