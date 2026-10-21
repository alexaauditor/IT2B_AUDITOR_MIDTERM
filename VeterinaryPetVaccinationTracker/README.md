# Veterinary Pet Registration & Vaccination Tracker

A Java console application for registering pet vaccination records and viewing saved records. It uses SQLite and a strict layered DAO design.

## Requirements

- IntelliJ IDEA
- JDK 17 or newer
- Maven (IntelliJ can import the included `pom.xml` and resolve dependencies)

## Features

1. Add Pet Record
2. View Vaccination Records
3. Exit System

Each record contains an automatically generated ID, pet name, species, vaccine type, and service fee. Species and vaccine choices are validated. Fees are displayed with two decimal places. Records are stored persistently in `pet_vaccination.db` in the application's working directory.

## Architecture

- `model/Pet.java` — data model
- `db/DatabaseConnection.java` — SQLite connection and schema initialization
- `dao/PetDAO.java` — data access contract
- `dao/PetDAOImpl.java` — SQLite persistence implementation
- `service/PetService.java` — business rules and validation
- `ui/ConsoleUI.java` — console prompts and formatted table
- `Main.java` — application entry point

The UI does not execute SQL. It calls the service layer; the service calls the DAO interface; the DAO implementation uses the database connection utility.

## Run in IntelliJ IDEA

1. Extract the ZIP file.
2. Open IntelliJ IDEA and select **Open**.
3. Select the extracted `VeterinaryPetVaccinationTracker` folder (the one containing `pom.xml`).
4. If prompted, select **Open as Project** and allow Maven to load dependencies.
5. Set the project SDK to JDK 17 or newer under **File → Project Structure → Project**.
6. Open `src/main/java/com/petclinic/Main.java`.
7. Click the green Run icon next to `main`.
8. The SQLite file `pet_vaccination.db` will be created in the application's working directory when the program starts.

If Maven dependencies do not load, open the Maven tool window and click **Reload All Maven Projects**.

## Validation rules

- Pet name cannot be blank.
- Species must be DOG, CAT, or BIRD (case-insensitive input).
- Vaccine type must be RABIES, DHPP, or FVRCP (case-insensitive input).
- Service fee must be a finite numeric value greater than or equal to zero.
- Invalid values are not saved.

## Suggested manual test plan

1. Start the program; verify the three menu options.
2. Choose `1` and enter `Milo`, `DOG`, `RABIES`, `500.00`.
3. Choose `1` again and enter `Luna`, `CAT`, `FVRCP`, `650.00`.
4. Choose `2`; verify both records, IDs 1 and 2 on a fresh database, and `Total Records Displayed: 2`.
5. Exit and run the program again; choose `2` to verify records persist.
6. Try a blank name, invalid species (`FISH`), invalid vaccine (`FLU`), nonnumeric fee (`abc`), and a negative fee. Confirm invalid data is not saved.
7. Choose `3` and verify `Exiting system... Thank you!`.

Note: IDs start at 1 on a fresh database. If you have already inserted and deleted records, SQLite's auto-generated IDs may continue from a higher value.

## GitHub submission

1. Create a **public** GitHub repository using the exact name required by your instructor. Your assignment screenshot mentions a naming format, so confirm the exact repository name before creating it.
2. In IntelliJ, use **VCS → Enable Version Control Integration → Git** if Git is not already enabled.
3. Add and commit the project files. Do not commit IDE-specific caches or build output.
4. Add your GitHub repository as the remote and push the commit, or use IntelliJ's GitHub sharing workflow.
5. Open the repository page in a browser and confirm that `pom.xml`, `README.md`, and `src/` are visible.
6. Capture the two screenshots required by your instructor:
   - Console execution showing a successful insertion and the menu/exit flow.
   - Database verification showing the saved SQLite records (for example, inspect `pet_vaccination.db` using a SQLite viewer or database tool).
7. Submit the public repository URL and both proof screenshots as instructed.

## Important submission note

The assignment screenshot refers to a required repository naming format and two proof-of-execution screenshots. Follow the exact naming convention and submission location given by your instructor. Do not include passwords or unrelated personal files in the repository.
