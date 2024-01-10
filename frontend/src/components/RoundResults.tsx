import {useState} from "react";
import axios from "axios";

export default function RoundResults() {

    const [results, setResults] = useState([])

    function getResultsOfPlayer(playerId: number) {
        axios.get("/api/game/1/results")
            .then(response => setResults(response.data))
        console.log(results)
    }

    return (
        <button onClick={getResultsOfPlayer}>Click</button>
    )
}