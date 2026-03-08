# import requests
# import json
# from block_a_and_b.src.app.config import Config

# class OllamaClient:
#     def __init__(self, llm, config: Config):
#         self.url = f"{config.OLLAMA_URL}/api/generate"
#         # self.model = config.OLLAMA_MODEL
#         self.model = llm
#         self.temperature = config.TEMPERATURE

#     def generate(self, prompt: str, stream: bool = False):
#         print(f"Sending prompt to Ollama ({self.model})...")
        
#         try:
#             response = requests.post(
#                 self.url,
#                 json={
#                     "model": self.model,
#                     "prompt": prompt,
#                     "stream": stream,
#                     "options": {
#                         "temperature": self.temperature,
#                         "num_predict": 4096,
#                     }
#                 },
#                 timeout=300
#             )
#             response.raise_for_status()

#             if stream:
#                 for line in response.iter_lines():
#                     if line:
#                         data = json.loads(line.decode("utf-8"))
#                         if "response" in data:
#                             yield data["response"]
#             else:
#                 return response.json()["response"]


#         except requests.exceptions.ConnectionError:
#             raise RuntimeError(
#                 "Cannot connect to Ollama. Is it running? Try: ollama serve"
#             )
#         except requests.exceptions.Timeout:
#             raise RuntimeError(
#                 "Ollama timed out. The model may be too slow"
#             )
#         except requests.exceptions.HTTPError as e:
#             raise RuntimeError(f"Ollama returned an error: {e.response.status_code} {e.response.text}")
#         except KeyError:
#             raise RuntimeError(f"Unexpected response format from Ollama: {response.json()}")

import requests
import json
import time
from block_a_and_b.src.app.config import Config

class OllamaClient:
    def __init__(self, llm, config: Config):
        self.url = f"{config.OLLAMA_URL}/api/generate"
        self.model = llm
        self.temperature = config.TEMPERATURE

    def generate(self, prompt: str) -> str:
        print(f"\n{'='*60}")
        print(f"  Model   : {self.model}")
        print(f"  Prompt  : {len(prompt)} chars (~{len(prompt)//4} tokens)")
        print(f"{'='*60}\n")

        try:
            response = requests.post(
                self.url,
                json={
                    "model": self.model,
                    "prompt": prompt,
                    "stream": True,
                    "options": {
                        "temperature": self.temperature,
                        "num_predict": 4096,
                        "num_ctx": 16384,
                    }
                },
                stream=True,
                timeout=300
            )
            response.raise_for_status()

            print("── LLM Output ──────────────────────────────────────────")
            full_response = []
            token_count = 0
            start_time = time.time()

            for line in response.iter_lines():
                if line:
                    chunk = json.loads(line)
                    token = chunk.get("response", "")
                    print(token, end="", flush=True)
                    full_response.append(token)
                    token_count += 1

                    if chunk.get("done", False):
                        elapsed = time.time() - start_time
                        eval_count = chunk.get("eval_count", token_count)
                        prompt_eval_count = chunk.get("prompt_eval_count", "?")
                        tokens_per_sec = eval_count / elapsed if elapsed > 0 else 0

                        print(f"\n\n── Generation Stats ─────────────────────────────────────")
                        print(f"  Time elapsed     : {elapsed:.1f}s")
                        print(f"  Prompt tokens    : {prompt_eval_count}")
                        print(f"  Generated tokens : {eval_count}")
                        print(f"  Speed            : {tokens_per_sec:.1f} tok/s")
                        print(f"{'='*60}\n")
                        break

            return "".join(full_response)

        except requests.exceptions.ConnectionError:
            raise RuntimeError("Cannot connect to Ollama. Is it running? Try: ollama serve")
        except requests.exceptions.Timeout:
            raise RuntimeError("Ollama timed out. The model may be too slow")
        except requests.exceptions.HTTPError as e:
            raise RuntimeError(f"Ollama returned an error: {e.response.status_code} {e.response.text}")
        except KeyError:
            raise RuntimeError(f"Unexpected response format from Ollama: {chunk}")