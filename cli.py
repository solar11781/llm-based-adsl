import argparse
from app.config import config
from app.prompts.prompt_builder import PromptBuilder
from app.llm.ollama_client import OllamaClient
from app.generator.filewriter import FileWriter

METAMODEL_MAP = {
    "dcsl": config.DCSL_PATH,
    "mccl": config.MCCL_PATH,
    "agl":  config.AGL_PATH,
}

def main():
    parser = argparse.ArgumentParser(description="Generate Java code from PUML using Ollama")
    parser.add_argument("--domain",       required=True,  help="Path to domain PUML file")
    parser.add_argument("--metamodel",    required=True,  choices=["dcsl", "mccl", "agl"], help="Which metamodel to use: dcsl | mccl | agl")
    parser.add_argument("--llm", required=True, help="Your Ollama Model name")
    parser.add_argument("--requirements", required=False, help="Path to requirements doc (optional)")
    parser.add_argument("--output",       required=False, help="Output file path (optional)")
    args = parser.parse_args()

    metamodel_path = METAMODEL_MAP[args.metamodel]
    print(f"Using metamodel: {args.metamodel} → {metamodel_path}")

    # 1. Build prompt
    print("Building prompt...")
    builder = PromptBuilder(config.PROMPT_TEMPLATE)
    prompt = builder.build(
        domain_puml_path=args.domain,
        metamodel_puml_path=metamodel_path,
        requirements_path=args.requirements
    )

    # 2. Call Ollama
    response = OllamaClient(args.llm, config).generate(prompt)

    # 3. Write raw response directly
    output_path = args.output or config.OUTPUT_DIR
    FileWriter().write_project(response, output_path)
    print(f"Done → {output_path}")

if __name__ == "__main__":
    main()