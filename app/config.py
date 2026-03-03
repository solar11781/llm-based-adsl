from pydantic_settings import BaseSettings

class Config(BaseSettings):
    OLLAMA_URL: str = "http://localhost:11434"
    OLLAMA_MODEL: str = "qwen2.5:3b"  # "gemma:7b"
    TEMPERATURE: float = 0.3
    ADSL_DIR: str = "./meta_models"
    OUTPUT_DIR: str = "./output"
    PROMPT_TEMPLATE: str = "./app/prompt/templates/PUML_to_Java_prompt.txt"

    class Config:
        env_file = ".env"

config = Config()