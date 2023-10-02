 <h1>BlackJack Game Java Swing</h1>

<h2>Introduction</h2>
<p>This is a simple Java-based implementation of the classic card game, Blackjack. The game is designed with a graphical user interface (GUI) using Java Swing, allowing you to play Blackjack against the computer dealer.</p>

<h2>Game Overview</h2>
<p>Blackjack is a card game where the objective is to have a hand value as close to 21 as possible without exceeding it. In this implementation, you play against a computer dealer. Here's a brief overview of the game's features and components:</p>

<ul>
    <li><strong>Game Interface:</strong> The game interface provides buttons for actions such as "Hit," "Stay," "Start," "Exit," and "New Round." The main game panel displays the current state of the game, including your hand, the dealer's hand, and the game's progress.</li>
    <li><strong>Chips:</strong> You can select a chip value to bet before starting each round. Your balance is initially set to 1000, and you can adjust the chip value accordingly.</li>
    <li><strong>Rounds:</strong> Each game round starts with a new deck of cards, and you can play multiple rounds. The game keeps track of your balance and the round number.</li>
    <li><strong>Game Rules:</strong> The game follows standard Blackjack rules, where face cards are worth 10 points, numbered cards are worth their face value, and Aces can be worth 1 or 11 points, whichever benefits the player more.</li>
</ul>

<h2>Getting Started</h2>
<p>To play the game, follow these steps:</p>

<ol>
    <li><strong>Starting the Game:</strong> Launch the game by running the <code>FunctionalController</code> class. This will open a graphical interface for Blackjack.</li>
    <li><strong>Select Chip Value:</strong> Choose the chip value you want to bet for the current round. You can adjust the chip value using the displayed buttons.</li>
    <li><strong>Starting a Round:</strong> Click the "Start" button to begin a new round. You will receive two initial cards, and the dealer will also receive a card.</li>
    <li><strong>Game Actions:</strong> During your turn, you can choose to "Hit" (draw another card) or "Stay" (end your turn). The objective is to get a hand value as close to 21 as possible without exceeding it.</li>
    <li><strong>Round Outcome:</strong> The game will automatically evaluate the round based on standard Blackjack rules. The round can end with various outcomes, such as a win, loss, or tie.</li>
    <li><strong>New Round:</strong> After each round, you can choose to start a new round by clicking the "New Round" button.</li>
    <li><strong>Exit:</strong> If you wish to exit the game, simply click the "Exit" button.</li>
</ol>

<p><em>Your balance is displayed at the top of the game interface.</em></p>
<p><em>The game includes logic to handle card decks, player actions, and round outcomes.</em></p>
<p><em>The game will continue until you decide to exit.</em></p>

<p>Enjoy playing Blackjack, and may the odds be in your favor!</p>
