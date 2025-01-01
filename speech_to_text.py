

import assemblyai as aai

# Replace with your API key
aai.settings.api_key = "d23b16f061014a1e92f78051b0b90bcf"


season = input("enter season e.g. (Season 6): ")
seria_name = input("enter seria name(arhcer only) e.g. (Archer): ")
seai = input("enter season e.g. (s6): ")
epi = input("enter episode e.g. (-e9): ")
# URL of the file to transcribe/ file path
#FILE_URL = r"C:\Users\Lesego\Desktop\things\Anime\Archer\Season 6\Archer-s6-e9_360p.mp4" using file path
FILE_URL = fr"C:\Users\Lesego\Desktop\things\Anime\Archer\{season}\{seria_name}-{seai}-{epi}_360p.mp4"     # using input path                                   
 #"https://assembly.ai/wildfires.mp3"

# You can also transcribe a local file by passing in a file path
# FILE_URL = './path/to/file.mp3'

transcriber = aai.Transcriber()
transcript = transcriber.transcribe(FILE_URL)

if transcript.status == aai.TranscriptStatus.error:
    print(transcript.error)
else:
    print(transcript.text)
