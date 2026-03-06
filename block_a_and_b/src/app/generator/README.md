# Contains FileW riter and Response Parser to write and parse LLM's reponse to Java files

**ResponseParser** takes the raw LLM text output and splits it into structured JavaFile objects using the /* FILE: */ markers.

**FileWriter** takes those structured objects and writes them to disk as actual files.