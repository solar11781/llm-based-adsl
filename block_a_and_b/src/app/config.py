from pydantic_settings import BaseSettings

class Config(BaseSettings):
    OLLAMA_URL: str = "http://localhost:11434"
    TEMPERATURE: float = 0.3
    DCSL_PATH: str = "../input/meta-models/DCSL.puml"
    MCCL_PATH: str = "../input/meta-models/MCCL.puml"
    AGL_PATH: str = "../input/meta-models/AGL.puml"
    OUTPUT_DIR: str = "../output"
    PROMPT_TEMPLATE: str = "../input/prompts/DCSL_to_Java.txt"

    class Config:
        env_file = ".env"

config = Config()