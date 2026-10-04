# LZW Compression, Encoding, and Decoding

A Java implementation of the **Lempel–Ziv–Welch (LZW)** compression algorithm. The project provides tools to:

- Encode hexadecimal input into LZW phrase numbers.
- Decode phrase numbers back into hexadecimal data.
- Pack variable-width phrase numbers into a binary byte stream.
- Unpack the binary stream back into phrase numbers.

The project was created by **Liam Labuschagne** and **Alexander Stokes** for a paper at the University of Waikato.

## Supported input

The encoder is designed for hexadecimal input using the characters:

```text
0 1 2 3 4 5 6 7 8 9 A B C D E F
```

Input is converted to uppercase. Newline characters are ignored. Other characters are not treated as valid input and may terminate encoding early.

## Project structure

```text
src/com/skwangles/
├── LZWencode.java   # Hexadecimal input → LZW phrase numbers
├── LZWdecode.java   # LZW phrase numbers → hexadecimal output
├── LZWpack.java     # Phrase numbers → packed binary bytes
├── LZWunpack.java   # Packed binary bytes → phrase numbers
├── runme.sh         # Convenience script, if available for the local environment
└── thisbreaksit.txt # Test or development data
```

## How the pipeline works

The complete compression pipeline is:

```text
Hexadecimal input
    ↓
LZWencode
    ↓
LZW phrase numbers
    ↓
LZWpack
    ↓
Packed binary output
```

To reverse the process:

```text
Packed binary input
    ↓
LZWunpack
    ↓
LZW phrase numbers
    ↓
LZWdecode
    ↓
Hexadecimal output
```

## Requirements

- Java Development Kit (JDK)
- A shell capable of piping standard input and output

The source uses standard Java libraries and does not require third-party dependencies.

## Compile the project

From the repository root, compile the Java sources into the current directory:

```bash
javac -d . src/com/skwangles/LZWencode.java
javac -d . src/com/skwangles/LZWdecode.java
javac -d . src/com/skwangles/LZWpack.java
javac -d . src/com/skwangles/LZWunpack.java
```

This creates compiled classes under `com/skwangles/`.

## Run the tools

After compiling, run the classes using their fully qualified names:

```bash
java com.skwangles.LZWencode
java com.skwangles.LZWdecode
java com.skwangles.LZWpack
java com.skwangles.LZWunpack
```

Each tool reads from standard input and writes to standard output, making the programs suitable for Unix-style pipelines.

## Examples

### Encode hexadecimal input

```bash
echo "AAABC00FFA2" | java com.skwangles.LZWencode
```

The encoder writes one LZW phrase number per line.

### Decode phrase numbers

```bash
echo "0
0
1" | java com.skwangles.LZWdecode
```

The decoder reads phrase numbers and writes the corresponding hexadecimal characters.

### Encode and decode in one pipeline

```bash
echo "AAABC00FFA2" \
  | java com.skwangles.LZWencode \
  | java com.skwangles.LZWdecode
```

The decoded output should represent the original hexadecimal input.

### Pack and unpack phrase numbers

To test encoding, bitpacking, unpacking, and decoding together:

```bash
cat test.txt \
  | java com.skwangles.LZWencode \
  | java com.skwangles.LZWpack \
  | java com.skwangles.LZWunpack \
  | java com.skwangles.LZWdecode \
  > out.hex
```

`LZWpack` writes binary bytes, so redirect packed output to a file or pipe it directly into `LZWunpack`. Avoid displaying packed output directly in a terminal.

## Bitpacking

The packer stores phrase numbers using a variable number of bits. As the dictionary grows, the number of bits used for each phrase number increases. `LZWunpack` follows the same dictionary-size progression to reconstruct the original phrase-number stream.

The packing format reserves zero as an escape or padding value, so phrase numbers are shifted during packing and unpacking.

## Known limitations

- The implementation is specialized for hexadecimal input rather than arbitrary text or binary data.
- The current bitpacker/unpacker has a known limitation when the dictionary grows beyond 256 phrase numbers. Phrase values requiring more than a single byte are not handled reliably.
- Packed output is binary and should be redirected to a file or passed directly to `LZWunpack`.
- The command-line tools use standard input and output rather than accepting file paths as arguments.
- This is an educational implementation and has not been optimized for large-scale production use.

## Contributors

- **Alexander Stokes** — encoder and bitpacker
- **Liam Labuschagne** — decoder and bitunpacker

## License

No license is currently specified for this repository. Contact the repository owners before redistributing or reusing the code.
