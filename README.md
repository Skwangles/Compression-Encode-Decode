# COMPX301-LZW-Encode-Decode
LZW Compression algorithm - Encodes, Decodes (Extra feature: Bitpacking included)

cat \<file\> | java LZWencode.java -> will encode HEX (0-9A-F) to a series of LZW Phrase Numbers

cat \<file\> | java LZWdecode.java -> will decode LZW Phrase Numbers to HEX

# Compiling for faster execution
```
javac -d . src/com/skwangles/LZWencode.java
javac -d . src/com/skwangles/LZWdecode.java
```
To execute just run:

`java com.skwangles.LZWencode` for encoding

or

`java com.skwangles.LZWdecode` for decoding


## Test encoding/decoding in one line:
`echo "AAABC00FFA2" | java LZWencode.java | java LZWdecode.java`

## Doing it a ridiculous number of times:
```echo "AAABC00FFA2" | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java | java LZWencode.java | java LZWdecode.java```

## Test encoding, packing, unpacking and decoding in one line:
`cat test.txt | java LZWencode.java | java LZWpack.java | java LZWunpack.java | java LZWdecode.java > out.hex`