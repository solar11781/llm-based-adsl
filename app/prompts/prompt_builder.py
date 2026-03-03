from pathlib import Path

class PromptBuilder:
    def __init__(self, template_path: str):
        self.template = Path(template_path).read_text()

    def build(
        self,
        domain_puml_path: str,
        metamodel_puml_path: str,
        requirements_path: str = None
    ) -> str:
        domain_puml    = self._load(domain_puml_path)
        metamodel_puml = self._load(metamodel_puml_path)
        requirements   = self._load(requirements_path) if requirements_path else "Not provided."

        # Slot into the exact placeholder the template expects at the bottom
        file_block = (
            f"- Domain PUML: {domain_puml_path}\n{domain_puml}\n\n"
            f"- Meta-model PUML: {metamodel_puml_path}\n{metamodel_puml}\n\n"
            f"- Requirements Doc (optional): "
            + (f"{requirements_path}\n{requirements}" if requirements_path else "Not provided.")
        )

        return self.template.replace(
            "- Domain PUML: <path + content>\n"
            "- Meta-model PUML: <path + content>\n"
            "- Requirements Doc (optional): <path + content or extracted text>",
            file_block
        )

    def _load(self, path: str) -> str:
        return Path(path).read_text(encoding="utf-8")
    
# Example Use:
# from app.prompts.prompt_builder import PromptBuilder

# # 1. Initialize with the path to your prompt template
# builder = PromptBuilder("app/prompts/PUML_to_Java_prompt.txt")

# # 2. Build the full prompt by providing paths to your specific files
# # We'll use AGL_PUML as your first implemented aDSL scale-up.
# final_prompt = builder.build(
#     domain_puml_path="tests/sample_inputs/courseman.puml",
#     metamodel_puml_path="meta_models/AGL_PUML.puml",
#     requirements_path=None  # Optional, can be omitted if not needed
# )

# # 3. Print or send this to your Ollama client
# print(final_prompt)