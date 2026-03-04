from pathlib import Path

class FileWriter:
    def write_project(self, raw_response: str, output_dir: str) -> str:
        output = Path(output_dir)
        output.mkdir(parents=True, exist_ok=True)
        
        out_file = output / "generated.java"
        out_file.write_text(raw_response, encoding="utf-8")
        print(f"wrote → {out_file}")
        
        return str(out_file)