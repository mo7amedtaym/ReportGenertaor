# -*- coding: utf-8 -*-
import sys
import argparse
import json
import os
import subprocess
from docxtpl import DocxTemplate
from typing import List, Dict


def create_docx(
    template_path: str,
    output_path: str,
    action: int,
    date: str,
    time: str,
    pro: str,
    sec: str,
    num: str,
    year: str,
    department: str,
    cap_title: str,
    cap_name: str,
    d_date: str,
    d_time: str,
    car_type: str,
    plate: str,
    unit: str,
    owner: str,
    name: str,
    address: str,
    age: str,
    job: str,
    id: str,
    questions: List[Dict[str, str]],
    decisions: List[str],
    instructions: List[str]
):
    doc = DocxTemplate(template_path)
    
    context = {
        "date": date,
        "time": time,
        "pro": pro,
        "sec": sec,
        "num": num,
        "year": year,
        "department": department,
        "cap_title": cap_title,
        "cap_name": cap_name,
        "d_date": d_date,
        "d_time": d_time,
        "car_type": car_type,
        "plate": plate,
        "unit": unit,
        "owner": owner,
        "name": name,
        "address": address,
        "age": age,
        "job": job,
        "id": id,
        "questions": questions,
        "decisions": decisions,
        "instructions": instructions
    }

    doc.render(context)
    doc.save(output_path)
    os.startfile(output_path, "print")
    print(f"[yes] DOCX report saved successfully. action is {action}")

    if action == 2:
        print("[info] Opening the file...")
        os.startfile(output_path)  # Windows only
    elif action == 3:
        print("[info] Sending file to printer...")
        try:
            subprocess.run(['cmd', '/c', 'print', output_path], check=True)
        except Exception as e:
            print(f"[no] Error printing the file: {e}", file=sys.stderr)


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Generate a police investigation DOCX report.")
    parser.add_argument("template", help="Path to the input DOCX template file")
    parser.add_argument("output", help="Path to the output DOCX file")
    parser.add_argument("json_data", help="Path to a JSON file containing the report data")
    parser.add_argument("--action", type=int, choices=[1, 2, 3], default=1,
                        help="1 = Save only, 2 = Save and open, 3 = Save and print")

    args = parser.parse_args()

    try:
        with open(args.json_data, "r", encoding="utf-8") as f:
            data = json.load(f)

        # Inject action into the data dictionary
        data["action"] = args.action

        create_docx(
            template_path=args.template,
            output_path=args.output,
            **data
        )

    except Exception as e:
        print(f"[no] Error: {e}", file=sys.stderr)
        sys.exit(1)
