from pydantic_settings import BaseSettings

class Config(BaseSettings):
    OLLAMA_URL: str = "http://localhost:11434"
    OLLAMA_MODEL: str = "qwen2.5-coder:3b"    #"qwen2.5:3b"  # "gemma:7b"
    TEMPERATURE: float = 0.3
    # ADSL_DIR: str = "./meta_models"
    DCSL_PATH: str = "./meta_models/DCSL_PUML.puml"
    MCCL_PATH: str = "./meta_models/MCCL_PUML.puml"
    AGL_PATH: str = "./meta_models/AGL_PUML.puml"
    OUTPUT_DIR: str = "./output"
    PROMPT_TEMPLATE: str = "./app/prompts/PUML_to_Java_prompt.txt"

    class Config:
        env_file = ".env"

config = Config()