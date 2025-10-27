import os

structure = {
    "dirA": {
        "file1.txt": "",
        "dirB": {
            "File1.TXT": ""
        }
    },
    "file2.java": "",
    "file3.log": ""
}

def create_structure(base_path, struct):
    for name, content in struct.items():
        path = os.path.join(base_path, name)
        if isinstance(content, dict):
            os.makedirs(path, exist_ok=True)
            create_structure(path, content)
        else:
            os.makedirs(os.path.dirname(path), exist_ok=True)
            with open(path, "w") as f:
                f.write(content)

if __name__ == "__main__":
    root = "temp_root"
    create_structure(root, structure)