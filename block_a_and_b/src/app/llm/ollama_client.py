import requests
import json
from app.config import Config

class OllamaClient:
    def __init__(self, llm, config: Config):
        self.url = f"{config.OLLAMA_URL}/api/generate"
        # self.model = config.OLLAMA_MODEL
        self.model = llm
        self.temperature = config.TEMPERATURE

    def generate(self, prompt: str, stream: bool = False):
        print(f"Sending prompt to Ollama ({self.model})...")
        
        try:
            response = requests.post(
                self.url,
                json={
                    "model": self.model,
                    "prompt": prompt,
                    "stream": stream,
                    "options": {
                        "temperature": self.temperature,
                        "num_predict": 4096,
                    }
                },
                timeout=300
            )
            response.raise_for_status()

            if stream:
                for line in response.iter_lines():
                    if line:
                        data = json.loads(line.decode("utf-8"))
                        if "response" in data:
                            yield data["response"]
            else:
                return response.json()["response"]


        except requests.exceptions.ConnectionError:
            raise RuntimeError(
                "Cannot connect to Ollama. Is it running? Try: ollama serve"
            )
        except requests.exceptions.Timeout:
            raise RuntimeError(
                "Ollama timed out. The model may be too slow"
            )
        except requests.exceptions.HTTPError as e:
            raise RuntimeError(f"Ollama returned an error: {e.response.status_code} {e.response.text}")
        except KeyError:
            raise RuntimeError(f"Unexpected response format from Ollama: {response.json()}")