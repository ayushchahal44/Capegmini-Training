let userRuns = 0;
let opponentRuns = 0;
let isUserBatting = true;
let innings = 1;
let target = 0;
let lastBalls = [];

function renderToss() {
    document.getElementById("toss").innerHTML = `
        Choose:
        <button onclick="tossChoice('Heads')">Heads</button>
        <button onclick="tossChoice('Tails')">Tails</button>
    `;
}

function tossChoice(choice) {
    let toss = Math.random() < 0.5 ? "Heads" : "Tails";

    if (choice === toss) {
        document.getElementById("toss").innerHTML = `
            You won the toss <br>
            <button onclick="choose('bat')">Bat</button>
            <button onclick="choose('bowl')">Bowl</button>
        `;
    } else {
        let comp = Math.random() < 0.5 ? "bat" : "bowl";
        document.getElementById("toss").innerHTML =
            `You lost the toss. Opponent chose ${comp}`;
        choose(comp === "bat" ? "bowl" : "bat");
    }
}

function renderGameUI(extra = "") {
    document.getElementById("userPlay").innerHTML = `
        <h3>${isUserBatting ? "You are Batting" : "You are Bowling"}</h3>
        <p>Your Score: ${userRuns}</p>
        <p>Opponent Score: ${opponentRuns}</p>
        ${innings === 2 ? `<p>Target: ${target}</p>` : ""}
        <p>Last 6 Balls: ${lastBalls.join(" | ")}</p>
        ${extra}
        <input type="number" id="input" min="1" max="6">
    `;
    document.getElementById("input").focus();
}

function choose(type) {
    isUserBatting = type === "bat";
    renderGameUI();
}

function resetGame() {
    userRuns = 0;
    opponentRuns = 0;
    isUserBatting = true;
    innings = 1;
    target = 0;
    lastBalls = [];
    document.getElementById("userPlay").innerHTML = "";
    renderToss();
}

function play() {
    let inputBox = document.getElementById("input");
    let input = parseInt(inputBox.value);

    if (!input || input < 1 || input > 6) {
        inputBox.value = "";
        inputBox.focus();
        return;
    }

    let bot = Math.floor(Math.random() * 6) + 1;

    lastBalls.push(`You:${input}-Bot:${bot}`);
    if (lastBalls.length > 6) lastBalls.shift();

    let extra = `<br>You: ${input} | Bot: ${bot}`;

    if (input === bot) {
        extra += `<br>OUT`;
        if (innings === 1) {
            target = isUserBatting ? userRuns + 1 : opponentRuns + 1;
            innings = 2;
            isUserBatting = !isUserBatting;
            lastBalls = [];
            alert("Innings over. Target is " + target);
            renderGameUI();
            return;
        } else {
            renderGameUI(extra);
            endGame();
            return;
        }
    }

    if (isUserBatting) {
        userRuns += input;
        extra += `<br>Total: ${userRuns}`;
        if (innings === 2 && userRuns >= target) {
            renderGameUI(extra);
            endGame();
            return;
        }
    } else {
        opponentRuns += bot;
        extra += `<br>Opponent Total: ${opponentRuns}`;
        if (innings === 2 && opponentRuns >= target) {
            renderGameUI(extra);
            endGame();
            return;
        }
    }

    renderGameUI(extra);
}

document.addEventListener("keydown", function(e) {
    if (e.key >= "1" && e.key <= "6") {
        let inputBox = document.getElementById("input");
        if (inputBox) {
            inputBox.value = e.key;
            play();
        }
    }
});

function endGame() {
    let result = "";

    if (innings === 2) {
        if (userRuns >= target) result = "You Win";
        else if (opponentRuns >= target) result = "Opponent Wins";
        else if (userRuns > opponentRuns) result = "You Win";
        else if (userRuns < opponentRuns) result = "Opponent Wins";
        else result = "Draw";
    } else {
        if (userRuns > opponentRuns) result = "You Win";
        else if (userRuns < opponentRuns) result = "Opponent Wins";
        else result = "Draw";
    }

    document.getElementById("userPlay").innerHTML += `<br><h2>${result}</h2>`;

    setTimeout(() => {
        resetGame();
    }, 3000);
}