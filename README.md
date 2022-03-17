# COMPX301-LZW-Encode-Decode
LZW Compression algorithm - Encodes, Decodes (Extra feature: Bitpacking included)

cat \<file\> | java LZWencode -> will encode HEX (0-9A-F) to a series of LZW Phrase Numbers

cat \<file\> | java LZWdecode -> will decode LZW Phrase Numbers to HEX

# Test encoding/decoding in one line:
echo "AAABC00FFA2" | java LZWencode.java | java LZWdecode.java

# Doing it a ridiculous number of times:
echo "AAABC00FFA2" | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java