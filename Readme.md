# Abstract
This is the artifact for the FM 2026 paper "A Framework for the Interoperable Specification and Verification of Encapsulated Data Structures" by Wolfram Pfeifer, Werner Dietl, and Mattias Ulbrich. It contains the example project described in the paper, which consists of a Java client using three libraries (Cell, LinkedCellList, and IntTreeSet), verified cooperatively with the tools KeY (standard variant using Dynamic Frames), VeriFast, and a Universe Type Checker plus KeY (special variant using Universe Types). The artifact provides the source code of the example, specifications in the different involved languages, as well as proofs which can be reloaded (if the specific verification tool supports that).
For each tool, scripts are provided to reload/check the corresponding proofs. Java 21 or newer is needed by the involved verification tools (both KeY variants, Universe Type Checker, and citool), while VeriFast needs the package libgomp1 apart from those that are installed in a minimal Ubuntu 24.04 distribution. For convenience, we provide a docker image that can be used to run the tools. The proof replay runs on any standard machine with at least 1GB of RAM (tested with "docker run -m 1g --memory-swap 1g --cpus=1"), and should need about 4 min for checking all the proofs.
We apply for the following badges: Artifacts Evaluated Level 1 (Functional), Artifacts Available

# Installation and Smoke Tests
Java 21 or newer is needed by the involved verification tools (both KeY variants, Universe Type Checker, and citool), while VeriFast needs the package libgomp1 apart from those that are installed in a minimal Ubuntu 24.04 distribution.
We recommend using the docker image in the artifact to run the tools.

## Via Docker
Make sure that Docker is installed:
* [Linux](https://docs.docker.com/desktop/install/linux-install/)
* [Apple](https://docs.docker.com/desktop/install/mac-install/)
* [Windows](https://docs.docker.com/desktop/install/windows-install/)

Make sure that you are in the directory of the artifact (where this Readme is located) and run the following command:
```bash
docker run -v .:/mnt/encapsulation wolframpfeifer/encapsulation ./smokeTests.sh
```
This checks that the tools VeriFast, KeY, and the KeY variant for Universe Types can be run.
The output should print the usage messages of the corresponding tools.

Note that the current directory is mounted into the docker container via `-v .:/mnt/encapsulation`.

## Without Docker
Make sure that you have at least Java 21 installed. Also, depending on your system configuration, you might need to install GOMP (package libgomp1 for Ubuntu 24.04).

```bash
./smokeTests.sh
```
As in the case with Docker, it checks that the tools can be run correctly by printing their help messages.

# Structure of the Example
The example consists of a client class which uses multiple data structures: A cell class (small mutable container; containing an int in this case), a linked list of cells, and a set of ints, implemented as a binary tree.
Verification is conducted cooperatively with three different tools: VeriFast, KeY, and a type checker for Universe Encapsulation Types (a variant of the Universe Type System) implemented in the checker framework.

Due to the nature of our technique, different versions of the files for each verification tool are needed, which are found in the correspondingly named subfolders.

Files in the repo:
```
* client+key-interfaces
    * Client.java               (Client implementation, main verification target)
    * Cell.java                 (verification interface for class Cell)
    * IntTreeSet.java           (verification interface for class IntTreeSet)
    * LinkedCellList.java       (verification interface for class LinkedCellList)
    * *.proof                   (KeY proof of the Client, uses the verification interfaces)
* contractlib
    * LinkedCellList.clib       (abstract specification of Cell and LinkedCellList)
    * IntTreeSet.clib           (abstract specification of IntTreeSet)
* key
    * Cell.java                 (verification template for Cell, contains specs)
    * CellImpl.java             (verification stub, filled in by the user with implementation and additional specification)
    * *.proof                   (KeY proofs that CellImpl adheres to specs in Cell)
* universe
    * qual                      (contains definitions of the UET annotations, used by KeY for parsing)
        * Any.java
        * Payload.java
        * Rep.java
        * ...
    * IntTreeSet.java           (verification template for IntTreeSet, contains specs)
    * IntTreeSetImpl.java       (verification stub, filled in by the user with implementation and additional specification)
    * TreeNode.java             (used internally by IntTreeSetImpl as part of the set implementation)
    * *.proof                   (KeY proofs that CellImpl adheres to specs in Cell)
* verifast
    * LinkedCellList.java       (verification template for LinkedCellList, contains specs)
    * LinkedCellListImpl.java   (verification stub, filled in by the user with implementation and additional specification)
    * Cell.javaspec             (verification interface for class Cell)
    * cell.jarspec              ("bundle" for the specification of Cell, needed by VeriFast when referring to javaspec files)
    * sources.jarsrc            (entry file for verification with VeriFast)
* tools
    * rt                        (runtime files for verifast)
    * key-2.12.4-dev-exe.jar    (KeY build using Dynamic Frames)
    * key-2.12.4-UT-dev-exe.jar (KeY build using Universe Encapsulation Types)
    * verifast                  (verifast binary)
* check.sh                      (top-level check script loading all proofs / re-running all the verifiers)
* check*.sh                     (individual sub-scripts for the techniques used)
* Readme.md                     (this file)
```

## Involved Tools
The tools can be found as binaries in the `tools` folder.
* KeY (default Dynamic Frames version)
* KeY (UET version)
* citool (provides a commandline interface for the GUI tool KeY)
* VeriFast
* Checker for Universe Encapsulation Types (UET; implemented in the Checker-Framework)
* (not included, can be found here: https://github.com/Contract-LIB/contract-chameleon) Contract-Chameleon (for generating interfaces and stubs from the Contract-LIB specification)

# Workflow of the Cooperative Verification Technique
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

# Replaying/Checking the Proofs

We provide a script as the main task for running the tools and replaying/checking the proofs.
Make sure that the current location is the main directory of the artifact and run the script in `check.sh`.

Via Docker:
```bash
docker run -v .:/mnt/encapsulation wolframpfeifer/encapsulation ./check.sh
```

Without Docker:
```bash
./check.sh
```

## Manually Running the Checks (Optional)
For experimenting and understanding, this section explains how each individual tool is called.

TODO: section has to be updated and is unfinished

### Verification with VeriFast (Separation Logic)

Run VeriFast on the provided file to check all assertions/contracts to (re-)verify the class `IntTreeSet` (and its nested data structure class `TreeNode`).
```bash
./tools/verifast -c -allow_dead_code -shared verifast/sources.jarsrc
```

### Verification of the Client with KeY (Dynamic Frames)
Run the following command to check that all the provided proofs are loadable.
```bash
java -jar tools/key-2.12.4-dev-exe.jar "--auto" "client+key-interfaces/example.Client(example.Client__m()).JML normal_behavior operation contract.0.proof"
```

### Verification of the Cell with KeY (Dynamic Frames)
Run the following command to check that all the provided proofs are loadable.
```bash
java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.Cell(example.Cell__init()).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.CellImpl(example.Cell__set(int)).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.CellImpl(example.Cell__value()).JML accessible clause.0.proof"
java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.CellImpl(example.Cell__value()).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.CellImpl(java.lang.Object___inv_()).JML accessible clause.0.proof"
```

### Verification with Universe Encapsulation Types + KeY
First, it should be checked that the type checker runs without any errors on the provided program:
```bash
./checkEnc.sh universe/*.java
```
Afterwards, for checking the functional specification load the proofs with the following command:
```bash
java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.IntTreeSet(example.IntTreeSet__init()).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.IntTreeSetImpl(example.IntTreeSet__add(int)).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.IntTreeSetImpl(example.IntTreeSet__contains(int)).JML accessible clause.0.proof"
java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.IntTreeSetImpl(example.IntTreeSet__contains(int)).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.IntTreeSetImpl(example.IntTreeSetImpl__IntTreeSetImpl()).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.IntTreeSetImpl(java.lang.Object___inv_()).JML accessible clause.0.proof"
java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.TreeNode(example.TreeNode__add(int)).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.TreeNode(example.TreeNode__contains(int)).JML accessible clause.0.proof"
java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.TreeNode(example.TreeNode__contains(int)).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.TreeNode(example.TreeNode__TreeNode(int)).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.TreeNode(java.lang.Object___inv_()).JML accessible clause.0.proof"
```
Note that this is a different KeY version than the one used with Dynamic Frames. This version makes use of the type and effect annotations verified correct in the first step, and exploits this information to simplify the framing proofs significantly.

# Generating Interfaces and Stubs with Contract-Chameleon

At the moment, Chameleon is not included in this repo. It can be found in https://github.com/Contract-LIB/contract-chameleon.
Beware that regenerating the verification interfaces and templates might overwrite manual changes that have been done there.
In principle, no manual changes are needed, but the tool is currently not polished enough and lacks some features (such as explicit predicate families for VeriFast).
Likewise, be careful that regenerating the stubs (*Impl.java files) overwrites all implementations and additional specifications added there by the user.

The following lists the commands of Chameleon for the different techniques.

Generate the verification interfaces (for use in client verification):
```bash
# This file also contains Cell, since LinkedCellList uses it ...
gradle run --args="key-applicant contractlib/LinkedCellList.clib Cell client+key-interfaces/ICell.java"
gradle run --args="key-applicant contractlib/LinkedCellList.clib LinkedCellList client+key-interfaces/ILinkedCellList.java"

gradle run --args="key-applicant contractlib/IntTreeSet.clib IntTreeSet client+key-interfaces/IIntTreeset.java"
```

Generate the verification stubs and templates for KeY:
```bash
# This file also contains Cell, since LinkedCellList uses it ...
# should generate: key/Cell.java key/CellImpl.java
# note: We do not need LinkedCellList here, but it needs to be in the same file as Cell. How to select only Cell for export?
gradle run --args="vf-provider contractlib/LinkedCellList.clib key"
```

Note: The VeriFast support is very prototypical at the moment.
Generate the verification stubs and templates for VeriFast:
```bash
# This file also contains Cell, since LinkedCellList uses it ...
gradle run --args="vf-provider contractlib/LinkedCellList.clib verifast"
gradle run --args="vf-provider contractlib/LinkedCellList.clib verifast"
```

Note: Support for the UET variant of KeY is currently not yet implemented, but planned for the future.

# Support

If you struggle with any installation or verification step, please reach out to [Wolfram Pfeifer](https://formal.kastel.kit.edu/~pfeifer/).
