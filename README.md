# Rock-Paper-Scissors Game

This is a simple Rock-Paper-Scissors game implemented in Java. The game allows a human player to compete against an AI agent in a series of rounds. The game is run in a console environment, and the player interacts with the game through text input.

## Getting Started

### Prerequisites

To run this project, you'll need:

- Java Development Kit (JDK) 21 installed.
- A console or terminal to compile and run the application.

### Project Structure

Here is an overview of the key components in the project:

- **`com.imc.Main`**: The entry point of the application.
- **`com.imc.core.Game`**: The main class responsible for managing the game logic.
- **`com.imc.console.ConsoleGameSetup`**: Handles the setup of the game, including selecting the number of rounds.
- **`com.imc.console.ConsoleGameStateDisplay`**: Handles displaying game information to the console.
- **`com.imc.core.state.GameState`**: Keeps track of game's progress and updates the display with the current state.

### How to Run

1. **Compile the Code**

   Compile the code using the following command:

   ```bash
   ./mvnw clean package

2. **Run the Application**

   After compiling the code, you can run the application using the following command:

   ```bash
   java -cp target/paper-rock-scissors-1.0-SNAPSHOT.jar com.imc.Main

3. **Run the Application**

     The game will start, and you'll be prompted to enter the number of rounds and then make your move in each round.

   - When prompted, enter the number of rounds you wish to play.
   - For each round, enter your move (either "Rock", "Paper", or "Scissors") or use the corresponding number (1 for Rock, 2 for Paper, 3 for Scissors).
   - The game will display the results of each round and the final winner after all rounds are completed.


# Notes
The game supports basic input validation. If you enter an invalid move, the game will prompt you to enter a valid move.
The game is designed to be extendable. You can modify the AI's strategy by implementing different `MoveStrategy` classes and plugging them into the `GamePlayer` instances.

## Future Improvements
Add support for different game modes (e.g., `Player vs. Player`, `Agent vs. Agent`).
Implement a graphical user interface (GUI) to enhance the user experience.
Introduce more advanced AI strategies for a more challenging game.