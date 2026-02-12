# A Framework for the Interoperable Specification and Verification of Encapsulated Data Structures (Artifact)
## Abstract
This is the artifact for the FM 2026 paper "A Framework for the Interoperable Specification and Verification of Encapsulated Data Structures" by Wolfram Pfeifer, Werner Dietl, and Mattias Ulbrich. It contains the example project described in the paper, which consists of a Java client using three libraries (Cell, LinkedCellList, and IntTreeSet), verified cooperatively with the tools KeY (standard variant using Dynamic Frames), VeriFast, and a Universe Type Checker plus KeY (special variant using Universe Types). The artifact provides the source code of the example, specifications in the different involved languages, as well as proofs which can be reloaded (if the specific verification tool supports that).

For each tool, scripts/commands are provided to reload/check the corresponding proofs. Java 21 or newer is needed by the involved verification tools (both KeY variants, Universe Type Checker, and citool), while VeriFast needs the package libgomp1 apart from those that are installed in a minimal Ubuntu 24.04 distribution. For convenience, we provide a Docker image that can be used to run the tools. The proof replay runs on any standard machine with at least 1GB of RAM (tested with `docker run -m 1g --memory-swap 1g --cpus=1`), and should need about 4 min for checking all the proofs.

The full proof replay with the Docker container requires a machine with an amd64 architecture, since the involved tool VeriFast does not provide ARM binaries (despite quite some effort, we did not succeed to build Linux ARM binaries). However, everything except for VeriFast can be run also through our docker container for ARM, and ARM binaries for Mac exist for VeriFast and might be used (without Docker).
We apply for the following badges: Artifacts Evaluated Level 2 (Functional and Reusable), Artifacts Available

## Requirements
For the full proof replay, a machine with an amd64 architecture is needed.
The reason is that binaries for VeriFast are not available for Linux ARM (we invested some effort to try to build it our own, but did not succeed).
If you have a Mac with ARM, it should still be possible to run the corresponding binaries (included in the artifact) directly without the docker container.
Other operating systems on ARM are not supported.

## Installation and Smoke Tests
Java 21 or newer is needed by the involved verification tools (both KeY variants, Universe Type Checker, and citool), while VeriFast needs the package libgomp1 apart from those that are installed in a minimal Ubuntu 24.04 distribution.
We provide a docker image with these dependencies preinstalled in the artifact to run the tools.

Make sure that Docker is installed:
* [Linux](https://docs.docker.com/desktop/install/linux-install/)
* [Apple](https://docs.docker.com/desktop/install/mac-install/)
* [Windows](https://docs.docker.com/desktop/install/windows-install/)

Make sure that you are in the directory of the artifact (where this Readme is located).
Run the following command:
```bash
docker run -v .:/mnt/encapsulation wolframpfeifer/encapsulation ./smokeTests.sh
```

If you have a Mac on ARM, run:
```bash
docker run -v .:/mnt/encapsulation wolframpfeifer/encapsulation ./smokeTestsNoVeriFast.sh
```
Then call VeriFast directly:
```bash
tools/vf/verifast-26.01-macos-aarch/bin/verifast --help
```
This should print the usage messages of the corresponding tools (VeriFast, KeY, the UET type checker, and the KeY variant for Universe Types) and thus show that they can be executed correctly.

Note that the current directory is mounted into the docker container via `-v .:/mnt/encapsulation`, so changes outside of the container are directly in effect inside and vice versa.

## Workflow of the Cooperative Verification Technique
Our technique is applicable to verify programs where a client uses multiple data structures which are encapsulated (a precise formal definition of this notion is given in the paper).
The following workflow can be used:

* The user provides abstract specifications of the functionality of the data structure classes in the language Contract-LIB.
* The user provides an implementation of the client class using the data structures.
* The tool Contract-Chameleon is used to generate the following files for each data structure:
    1. a verification template with the interface and the specification of the data structure to be proven (depending on the technique that should be used for verification of that data structure)
    2. a verification stub (classes inheriting from the template from (1)), where the user needs to fill in implementation and additional specification (such as coupling invariants/predicates, loop invariants, ...)
    3. a verification interface containing the necessary specification to use the data structure for verification in the client (depending on the technique that should be used for verifying the client), or for the use in other verification templates
* The filled in stubs are then verified w.r.t. the verification templates (1), each with the concrete verification technique/tool chosen for it.
* The client is verified in the technique of choice, using the verification interfaces (3).

Note that at the moment, Contract-Chameleon is still a prototype and only supports export for some of the tools. Therefore, after generation of the language/tool-specific interfaces and stubs (step 3), manual changes had to be made. In the case of ...

## Structure of this Example
The example consists of a client class which uses multiple data structures: A cell class (small mutable container; containing an int in this case), a linked list of cells, and a set of ints, implemented as a binary tree.
Verification is conducted cooperatively with three different tools: VeriFast, KeY, and a type checker for Universe Encapsulation Types (a variant of the Universe Type System) implemented in the checker framework.

The different variants of the source files and specifications for each verification tool can be found in the correspondingly named subfolders.

Files in the artifact:
```
* client+key-interfaces
    * Client.java                       (Client implementation, main verification target)
    * Cell.java                         (verification interface for class Cell)
    * IntTreeSet.java                   (verification interface for class IntTreeSet)
    * LinkedCellList.java               (verification interface for class LinkedCellList)
    * *.proof                           (KeY proof of the Client, uses the verification interfaces)
* contractlib
    * LinkedCellList.clib               (abstract specification of Cell and LinkedCellList)
    * IntTreeSet.clib                   (abstract specification of IntTreeSet)
* docker
    * Dockerfile                        (file for building docker container that can be used to run the tools for proof checking)
* key
    * Cell.java                         (verification template for Cell, contains specs)
    * CellImpl.java                     (verification stub, filled in by the user with implementation and additional specification)
    * *.proof                           (KeY proofs that CellImpl adheres to specs in Cell)
* universe
    * qual                              (contains definitions of the UET annotations, used by KeY for parsing)
        * Any.java
        * Payload.java
        * Rep.java
        * ...
    * IntTreeSet.java                   (verification template for IntTreeSet, contains specs)
    * IntTreeSetImpl.java               (verification stub, filled in by the user with implementation and additional specification)
    * TreeNode.java                     (used internally by IntTreeSetImpl as part of the set implementation)
    * *.proof                           (KeY proofs that CellImpl adheres to specs in Cell)
* verifast
    * LinkedCellList.java               (verification template for LinkedCellList, contains specs)
    * LinkedCellListImpl.java           (verification stub, filled in by the user with implementation and additional specification)
    * Cell.javaspec                     (verification interface for class Cell)
    * cell.jarspec                      ("bundle" for the specification of Cell, needed by VeriFast when referring to javaspec files)
    * sources.jarsrc                    (entry file for verification with VeriFast)
* tools
    * key-2.12.4-dev-exe.jar            (KeY build using Dynamic Frames)
    * key-2.12.4-UT-dev-exe.jar         (KeY build using Universe Encapsulation Types)
    * citool-1.7.0-SNAPSHOT-mini.jar    (tool that provides a CLI for KeY)
    * uet-checker
        * checkEnc.sh                   (script to run the UET checker)
        * ...
    * vf
        * verifast-26.01-linux-amd64    (binaries and runtime files for VeriFast, runnable on Linux amd64)
        * verifast-26.01-macos-aarch    (binaries and runtime files for VeriFast, runnable on MacOS ARM)
    (verifast binary)
* check.sh                              (top-level check script loading all proofs / re-running all the verifiers)
* check*.sh                             (individual sub-scripts for the techniques used)
* Readme.md                             (this file)
```

### Involved Tools
The tools can be found as binaries/JARs in the `tools` folder.
* KeY (default Dynamic Frames version)
* KeY (UET version)
* citool (provides a commandline interface for the GUI tool KeY)
* VeriFast
* Checker for Universe Encapsulation Types (UET; implemented in the Checker-Framework)
* Contract-Chameleon (for generating interfaces and stubs from the Contract-LIB specification; not included, can be found here: https://github.com/Contract-LIB/contract-chameleon)

## Replaying/Checking the Proofs

We provide a script as the main task for running the tools and replaying/checking the proofs.
Make sure that the current location is the main directory of the artifact and run the script in `check.sh` via the provided Docker container:
```bash
docker run -v .:/mnt/encapsulation wolframpfeifer/encapsulation ./check.sh
```

On Mac ARM (run everything except for VeriFast through Docker:
```bash
docker run -v .:/mnt/encapsulation wolframpfeifer/encapsulation ./checkNoVeriFast.sh
tools/vf/verifast-26.01-macos-aarch/bin/verifast -c -allow_dead_code -shared verifast/sources.jarsrc
```

### Manually Running the Checks (Optional)
For experimenting and understanding, this section explains how each individual tool is called.

#### Verification with VeriFast (Separation Logic)

Run VeriFast on the provided file to check all assertions/contracts to (re-)verify the class `IntTreeSet` (and its nested data structure class `TreeNode`).
With amd64 architecture:
```bash
docker run -v .:/mnt/encapsulation wolframpfeifer/encapsulation '/tools/verifast-26.01-linux-amd64/bin/verifast -c -allow_dead_code -shared verifast/sources.jarsrc'
```
If you have Mac ARM, you can run the provided VeriFast binary for Mac ARM without Docker:
```bash
'/tools/verifast-26.01-mac-aarch/bin/verifast -c -allow_dead_code -shared verifast/sources.jarsrc'
```
We run verifast with the following options:
* `-c` to only do the compilation and verification, and skip the linking phase
* `-allow_dead_code` to skip checks for dead code, since some of the methods are not called
* `-shared` to mark that we verify a library and no main method is required
The call should succeed nearly immediately and print `0 errors found (36 statements verified)`.

#### Verification of the Client with KeY (Dynamic Frames)
Run the following command to check that all the provided proofs are loadable.
```bash
docker run -v .:/mnt/encapsulation wolframpfeifer/encapsulation 'java -Dslf4j.internal.verbosity=ERROR -Dlogback.configurationFile=tools/disablelogging.xml -cp "tools/key-2.12.4-dev-exe.jar:tools/citool-1.7.0-SNAPSHOT-mini.jar" io.github.wadoon.keycitool.CheckerKt -v --proof-path client+key-interfaces client+key-interfaces'
```
We are using citool (https://github.com/wadoon/key-citool) as a clean CLI for KeY, which is primarily a GUI tool.

#### Verification of the Cell with KeY (Dynamic Frames)
Run the following command to check that all the provided proofs are loadable.
```bash
docker run -v .:/mnt/encapsulation wolframpfeifer/encapsulation 'java -Dslf4j.internal.verbosity=ERROR -Dlogback.configurationFile=tools/disablelogging.xml -cp "tools/key-2.12.4-dev-exe.jar:tools/citool-1.7.0-SNAPSHOT-mini.jar" io.github.wadoon.keycitool.CheckerKt -v --proof-path key key'
```

#### Verification with Universe Encapsulation Types + KeY
First, it should be checked that the type checker runs without any errors on the provided program:
```bash
docker run -v .:/mnt/encapsulation wolframpfeifer/encapsulation 'tools/checkEnc.sh universe/*.java'
```
This runs the type checker for Universe Encapsulation Types (a stricter variant of the Universe Type system found at https://github.com/opprop/universe), and checks that the involved Java classes adhere to the Universe Encapsulation Types and schema as described in the paper.
If everything works correctly, this should print the Java compiler version, the command used for compiling/type checking, and then no further output (type errors would reflect as compiler errors).
Note that the checker is based on the checker framework and runs as an annotation processor during compilation with `javac`, and as a side effect the Java files are also compiled. However, in this verification project, we do not need the .class files.

Afterwards, for checking the functional specification load the proofs with the following command:
```bash
docker run -v .:/mnt/encapsulation wolframpfeifer/encapsulation 'java -Dslf4j.internal.verbosity=ERROR -Dlogback.configurationFile=tools/disablelogging.xml -cp "tools/key-2.12.4-UT-dev-exe.jar:tools/citool-1.7.0-SNAPSHOT-mini.jar" io.github.wadoon.keycitool.CheckerKt -v --proof-path key key'
```
Note that this runs a different KeY variant than the one used with Dynamic Frames. This variant makes use of the type and effect annotations verified correct in the first step, and exploits this information to simplify the framing proofs significantly.

## Generating Interfaces and Stubs with Contract-Chameleon

TODO: section unfinished

As mentioned above, Chameleon is still a prototype with only partial support for some of our use cases.
The 
Therefore, it is not included in this repo.
However, if you want to try it out anyways, it can be found in the repository https://github.com/Contract-LIB/contract-chameleon.
Beware that regenerating the verification interfaces and templates might overwrite manual changes that have been done there.
In principle, no manual changes are needed, but the tool does currently not have all the features needed for this case study (such as explicit predicate families for VeriFast).
Likewise, be careful that regenerating the stubs (*Impl.java files) overwrites all implementations and additional specifications added there by the user.


### Individual Contract-Chameleon Commands
The following lists the commands of Chameleon for the different techniques.

#### Client
The verification interfaces for use in client verification are generated with the following commands:
```bash
# This file also contains Cell, since LinkedCellList uses it ...
gradle run --args="key-applicant contractlib/LinkedCellList.clib Cell client+key-interfaces/ICell.java"
gradle run --args="key-applicant contractlib/LinkedCellList.clib LinkedCellList client+key-interfaces/ILinkedCellList.java"

gradle run --args="key-applicant contractlib/IntTreeSet.clib IntTreeSet client+key-interfaces/IIntTreeset.java"
```

#### KeY:
```bash
# This file also contains Cell, since LinkedCellList uses it ...
# should generate: key/Cell.java key/CellImpl.java
# note: We do not need LinkedCellList here, but it needs to be in the same file as Cell. How to select only Cell for export?
gradle run --args="vf-provider contractlib/LinkedCellList.clib key"
```

#### VeriFast
Note that VeriFast support is very prototypical at the moment. In particular, predicate families and additional functions (defined via "fixpoint" in VeriFast) are not supported at the moment.
Generate the verification stubs and templates for VeriFast:
```bash
# This file also contains Cell, since LinkedCellList uses it ...
gradle run --args="vf-provider contractlib/LinkedCellList.clib verifast"
gradle run --args="vf-provider contractlib/LinkedCellList.clib verifast"
```

#### KeY + Universe Encapsulation Types
Support for the UET variant of KeY is not yet implemented, but ongoing work.

# Support
If you struggle with any installation or verification step, please reach out to [Wolfram Pfeifer](https://formal.kastel.kit.edu/~pfeifer/).
