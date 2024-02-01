import {useEffect, useState} from "react";
import {SetOfPointsType} from "../types/SetOfPointsType.ts";
import axios from "axios";
import {useParams} from "react-router-dom";
import GameBox from "./GameBox.tsx";

export default function GameResult() {

    const playerId: string = "8162795f-5c82-44fc-a5ef-1cf5ce545f7b"

    const [gameResult, setGameResult] = useState<SetOfPointsType[]>([])

    const {gameId} = useParams();

    useEffect(() => {
        getGameResult();
    }, []);

    function getGameResult() {
        axios.get(`/api/game/${playerId}/${gameId}/gameResult`)
            .then(response => {
                setGameResult(response.data.setOfPoints)
            })
            .catch(error => {
                console.error("Request failed: ", error.response.status);
            })
    }

    return (
        <>
            {gameResult?.map((result) => (
                <GameBox key={result.trashCanId}
                         text={`${result.playerPoints} / ${result.dataPoints}`}
                         $backgroundColor={trashCanColors[result.trashCanId]}
                         color={"colorLight"}
                         size={"100px"}
                         $margin={"auto 5px"}
                />
            ))}
        </>
    )
}

const trashCanColors: {
    [key: string]: string
} = {
    "1": "#0071BC",
    "2": "#F7931E",
    "3": "#333333",
};
