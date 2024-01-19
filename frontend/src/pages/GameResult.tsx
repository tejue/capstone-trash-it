import {useEffect, useState} from "react";
import axios from "axios";
import {SetOfPointsType} from "../types/SetOfPointsType.ts";

export default function GameResult() {

    const playerId: string = "5"
    const gameId: string = "1"

    const [gameResult, setGameResult] = useState<SetOfPointsType[]>([])

    useEffect(() => {
        getGameResult();
    }, []);

    function getGameResult() {
        axios.get(`/api/game/${playerId}/${gameId}/gameResult`)
            .then(response => {
                setGameResult(response.data.setOfPoints)
            })
            .catch(error => {
                console.error("Request failed: ", error);
            })
    }

    console.log(gameResult);
    return(
        <>
            {gameResult?.map((result) => (
                <section key={result.trashCanId}>
                    <p>{result.playerPoints} / {result.dataPoints}</p>
                </section>
            ))}
        </>
    );
}
