# Java Code Generator — aDSL-Driven LLM App

> CLI Python app that uses a local Gemma (Ollama) LLM to generate Java project code from a user-supplied domain PUML, guided by your aDSL PUML template.

---

## Our Swinburne Software Lab:

Principal Investigator: Dr. Le Minh Duc  
Lab Supervisor: Nguyen Van Cong, MSc

AI Team Members:  
Bui Tran Gia Bao: Swinburne Hanoi (Graduated)  
Nguyen Ha Khue: Swinburne HCM  
Nguyen Duc Manh: Swinburne Hanoi

---

## Project Structure

```
llm-based-adsl/
│
├── meta_models/
│   ├── DCSL_PUML.puml           # (active)
│   ├── MCCL_PUML.puml           # future
│   └── MCCL_PUML.puml           # future
│
├── app/
│   ├── parsers/                   # NOT IMPLEMENTED YET
│   │   ├── adsl_parser.py         # Parses aDSL PUML → aDSLSchema
│   │   └── domain_parser.py       # Parses user domain PUML → list[DomainClass]
│   ├── prompt/
│   │   ├── prompt_builder.py      # Combines aDSL + domain → prompt string
│   │   └── PUML_to_Java_prompt.txt # System prompt template
│   ├── llm/
│   │   └── ollama_client.py       # HTTP client for Ollama /api/generate
│   ├── generator/
│   │   ├── response_parser.py     # Extracts Java file blocks from LLM response (NOT IMPLEMENTED YET)
│   │   └── file_writer.py         # Writes Java files + pom.xml to disk
│   ├── config.py                  # Paths, model name, Ollama URL
│   └── artifact/
│
├── tests/
│   └── sample_inputs/
│       └── course_manager.puml    # Sample domain PUML for testing
│
├── output/                        # Generated Java projects land here
├── cli.py                         # Entry point
├── requirements.txt
```

---

## Data Flow

```
domain.puml ──► DomainParser  ──┐
                                ├──► PromptBuilder ──► OllamaClient ──► ResponseParser ──► FileWriter
adsl_layer1.puml ──► aDSLParser ┘
```

---

## How To Run:

For now the app only generates Java OOP code in DCSL syntax.  
Later we will implement MCCL and AGL into the prompt template.  
To run, first make sure Ollama is running and you change the model name inside config.py. Then run:

```
python cli.py --domain .input/domain/courseman.puml --metamodel dcsl --llm [your Ollama LLM model, example: qwen2.5-coder:3b]
```

Example output:

```
Using metamodel: dcsl → ./meta_models/DCSL_PUML.puml
Building prompt...
Sending prompt to Ollama (qwen2.5-coder:3b)...
wrote → output\generated.java
Done → ./output
```

The generated code will land in **output/**
