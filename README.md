# Java Code Generator — aDSL-Driven LLM App

> CLI Python app that uses a local Gemma (Ollama) LLM to generate Java project code from a user-supplied domain PUML, guided by your aDSL PUML template.

---

## Project Structure

```
java-codegen/
│
├── adsl/
│   ├── adsl_layer1.puml           # aDSL #1 (active)
│   ├── adsl_layer2.puml           # future
│   └── adsl_layer3.puml           # future
│
├── app/
│   ├── parsers/
│   │   ├── adsl_parser.py         # Parses aDSL PUML → aDSLSchema
│   │   └── domain_parser.py       # Parses user domain PUML → list[DomainClass]
│   ├── prompt/
│   │   ├── prompt_builder.py      # Combines aDSL + domain → prompt string
│   │   └── templates/
│   │       └── codegen_prompt.txt # System prompt template
│   ├── llm/
│   │   └── ollama_client.py       # HTTP client for Ollama /api/generate
│   ├── generator/
│   │   ├── response_parser.py     # Extracts Java file blocks from LLM response
│   │   └── file_writer.py         # Writes Java files + pom.xml to disk
│   └── config.py                  # Paths, model name, Ollama URL
│
├── tests/
│   └── sample_inputs/
│       └── course_manager.puml    # Sample domain PUML for testing
│
├── output/                        # Generated Java projects land here
├── cli.py                         # Entry point
├── requirements.txt
└── .env.example
```

---

## Data Flow

```
domain.puml ──► DomainParser  ──┐
                                ├──► PromptBuilder ──► OllamaClient ──► ResponseParser ──► FileWriter
adsl_layer1.puml ──► aDSLParser ┘
```

---

## Component Breakdown

### `app/parsers/domain_parser.py`

Parses the user's domain PUML into structured data using regex — no external PUML lib needed.

```python
@dataclass
class Field:        name: str; type: str
@dataclass
class Method:       name: str; params: list[str]; return_type: str
@dataclass
class Relationship: source: str; kind: str; target: str  # e.g. EXTENDS, HAS, USES

@dataclass
class DomainClass:
    name: str
    fields: list[Field]
    methods: list[Method]
    relationships: list[Relationship]
    stereotypes: list[str]           # e.g. ["Entity", "Service"]
```

**What to parse:**
- `class ClassName` blocks and `<<Stereotype>>` tags
- Fields: `- fieldName : Type`
- Methods: `+ methodName(params) : ReturnType`
- Relationships: `Student --> Course`, `Student --|> Person`

---

### `app/parsers/adsl_parser.py`

Parses your aDSL PUML into the rules that drive code generation.

```python
@dataclass
class aDSLSchema:
    layers: list[str]          # e.g. ["controller", "service", "repository", "model"]
    naming_rules: dict         # e.g. {"service": "{Name}Service", "repo": "{Name}Repository"}
    package_structure: dict    # e.g. {"base": "com.{domain}", "layers": [...]}
    annotations: dict          # e.g. {"service": "@Service", "repo": "@Repository"}
    patterns: list[str]        # e.g. ["Repository", "DTO", "Builder"]
```

**What to parse:**
- Layer definitions from aDSL stereotypes/notes
- Naming conventions and package layout
- Required patterns per layer

---

### `app/prompt/prompt_builder.py` + `codegen_prompt.txt`

Converts parsed data into the LLM prompt.

```python
class PromptBuilder:
    def build(self, adsl: aDSLSchema, domain: list[DomainClass]) -> str:
        # Serializes both into a structured prompt string
```

**Template structure (`codegen_prompt.txt`):**
```
You are a Java code generator.

[aDSL RULES]
{adsl_summary}

[DOMAIN MODEL]
{domain_summary}

Generate a complete Java Maven project following the aDSL exactly.
- Output one block per file, each starting with: // FILE: src/main/java/{path}/{Name}.java
- No explanations between files. Code only.
```

> Keep total prompt under ~3000 tokens — Gemma on CPU is slow.

---

### `app/llm/ollama_client.py`

Plain `requests` call to Ollama — no SDK needed.

```python
class OllamaClient:
    def generate(self, prompt: str) -> str:
        # POST to /api/generate, return response text

# Key settings:
{
    "model": "gemma:7b",
    "stream": False,
    "options": { "temperature": 0.2, "num_predict": 4096 }
}
```

> Use `temperature: 0.1–0.3` for deterministic output. Set `timeout=180` for slow hardware.

---

### `app/generator/response_parser.py`

Splits the raw LLM response into individual Java files.

```python
@dataclass
class JavaFile:
    relative_path: str    # e.g. "src/main/java/com/uni/service/StudentService.java"
    content: str

class ResponseParser:
    def parse(self, llm_response: str) -> list[JavaFile]:
        # Find // FILE: markers, extract code blocks between them
        # Strip markdown fences (```java ... ```) if present
```

---

### `app/generator/file_writer.py`

Writes the parsed files to disk and adds Maven boilerplate.

```python
class FileWriter:
    def write_project(self, files: list[JavaFile], output_dir: str) -> str:
        # mkdir -p for each path, write each .java file
        # Also writes pom.xml and .gitignore
        # Returns the output path
```

---

### `cli.py`

```bash
python cli.py --domain path/to/domain.puml
python cli.py --domain path/to/domain.puml --adsl 1 --output ./my-project --package com.uni.cms
```

```python
def main():
    # 1. Parse args
    # 2. aDSLParser.parse(adsl/adsl_layer{n}.puml)
    # 3. DomainParser.parse(domain.puml)
    # 4. PromptBuilder.build(adsl, domain)
    # 5. OllamaClient.generate(prompt)
    # 6. ResponseParser.parse(response)
    # 7. FileWriter.write_project(files, output_dir)
    # 8. Print: "Generated N files -> ./output/project-name/"
```

---

### `app/config.py`

```python
from pydantic_settings import BaseSettings

class Config(BaseSettings):
    OLLAMA_URL: str = "http://localhost:11434"
    OLLAMA_MODEL: str = "gemma:7b"
    ADSL_DIR: str = "./adsl"
    OUTPUT_DIR: str = "./output"
    TEMPERATURE: float = 0.2

    class Config:
        env_file = ".env"
```

---

## Implementation Order

1. `domain_parser.py` — test against `course_manager.puml`
2. `adsl_parser.py` — test against `adsl_layer1.puml`
3. `codegen_prompt.txt` + `prompt_builder.py`
4. `ollama_client.py`
5. `response_parser.py`
6. `file_writer.py`
7. Wire in `cli.py` and run end-to-end

---

## Scaling to More aDSL PUMLs

- Drop `adsl_layer2.puml` / `adsl_layer3.puml` into `adsl/`
- `aDSLParser` already takes a path — just pass the right file
- `--adsl 2` CLI flag selects which one to load
- No other changes needed

---

## Setup

```bash
pip install -r requirements.txt

# Install Ollama + pull model
curl -fsSL https://ollama.com/install.sh | sh
ollama pull gemma:7b        # or gemma:2b on constrained hardware

cp .env.example .env
python cli.py --domain tests/sample_inputs/course_manager.puml
```

**`requirements.txt`**
```
requests>=2.31.0
pydantic>=2.7.0
pydantic-settings>=2.3.0
pytest>=8.2.0
```
