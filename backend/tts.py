import sys
import asyncio
import edge_tts


async def generate_speech(text, voice, output_file):
    communicate = edge_tts.Communicate(text, voice)
    await communicate.save(output_file)


if __name__ == "__main__":
    text = sys.argv[1]
    voice = sys.argv[2]
    output_file = sys.argv[3]

    asyncio.run(generate_speech(text, voice, output_file))