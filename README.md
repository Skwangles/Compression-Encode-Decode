Made by: Liam L & Alexander S for a paper at the University of Waikato.   
Alexander made the Encoder & the Bitpacker  
Liam made the decoder & the Bitunpacker  

# LZW-Encode-Decode
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


## Known issues
The bitpacker has an issue where the # of phrase numbers exceeds 256 (i.e. the max possible size of the next phrase will be more than a single byte) - the packer does not correctly interpret wrap the bits around and will get confused.
