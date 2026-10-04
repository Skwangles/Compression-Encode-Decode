# LZW Compression, Encoding, and Decoding

Java command-line tools for experimenting with a hexadecimal-focused implementation of **Lempel–Ziv–Welch (LZW)**.

This repository currently provides four stdin/stdout programs:

- `LZWencode` — hexadecimal input → LZW phrase numbers
- `LZWdecode` — phrase numbers → hexadecimal output
- `LZWpack` — phrase numbers → packed binary bytes
- `LZWunpack` — packed binary bytes → phrase numbers

Created by **Liam Labuschagne** and **Alexander Stokes** at the University of Waikato.

## Current features (Oct 4, 2026)

- Streams data through standard input/output (pipeline-friendly)
- Uppercases input for encoding
- Ignores newline characters during encoding
- Encodes/decodes using a hexadecimal alphabet (`0-9`, `A-F`)
- Packs/unpacks phrase numbers with variable-width bit encoding

## Technology stack

- **Language:** Java
- **Dependencies:** Java standard library only (no third-party dependencies)
- **Build system:** none (compile with `javac` directly)
- **Test framework:** none in repository (manual CLI verification)

## Repository structure

```text
src/com/skwangles/
├── LZWencode.java
├── LZWdecode.java
├── LZWpack.java
├── LZWunpack.java
├── runme.sh
└── thisbreaksit.txt
```

## Prerequisites

- JDK installed (`javac`, `java` available on `PATH`)
- Shell with stdin/stdout piping support

## Build / installation

From the repository root:

```bash
javac -d . src/com/skwangles/LZWencode.java \
          src/com/skwangles/LZWdecode.java \
          src/com/skwangles/LZWpack.java \
          src/com/skwangles/LZWunpack.java
```

This outputs `.class` files under `com/skwangles/`.

## Usage

Run each tool after compilation:

```bash
java com.skwangles.LZWencode
java com.skwangles.LZWdecode
java com.skwangles.LZWpack
java com.skwangles.LZWunpack
```

### Example: encode hexadecimal input

```bash
echo "AAABC00FFA2" | java com.skwangles.LZWencode
```

### Example: decode phrase numbers

```bash
printf "0\n0\n1\n" | java com.skwangles.LZWdecode
```

### Example: encode then decode

```bash
echo "AAABC00FFA2" \
  | java com.skwangles.LZWencode \
  | java com.skwangles.LZWdecode
```

### Example: full pack/unpack round-trip

```bash
echo "AAABC00FFA2" \
  | java com.skwangles.LZWencode \
  | java com.skwangles.LZWpack \
  | java com.skwangles.LZWunpack \
  | java com.skwangles.LZWdecode
```

> `LZWpack` emits binary bytes. Redirect to a file or pipe directly into `LZWunpack` instead of printing packed output directly to a terminal.

## Testing instructions

There is no automated test suite in this repository.

To verify behavior manually:

1. Compile all classes (see Build section).
2. Run the example pipelines above.
3. Optionally try the included sample file:

```bash
cat src/com/skwangles/thisbreaksit.txt | java com.skwangles.LZWencode
```

## Configuration details

- No config files or runtime flags are provided.
- Alphabet is hardcoded in `LZWencode` (`0-9`, `A-F`).
- Dictionary growth and bit-width progression are hardcoded in `LZWpack` and `LZWunpack`.

## Limitations / current status

- Implementation is specialized for hexadecimal streams (not a general-purpose text/binary compressor).
- Long pack/unpack/decode pipelines can fail once dictionary growth exceeds current implementation limits (observed during manual verification).
- Tools do not accept file-path arguments; they use stdin/stdout only.
- `src/com/skwangles/runme.sh` is present but does not match the package-qualified commands documented above.
- Educational project status; not production-hardened.

## Attribution

- **Alexander Stokes** — encoder and bitpacker
- **Liam Labuschagne** — decoder and bitunpacker

## License

No license file is currently included in this repository.
Contact the repository owner(s) before redistributing or reusing the code.
