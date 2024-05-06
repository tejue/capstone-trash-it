import {useEffect, useState} from "react";
import {SetOfPointsType} from "../types/SetOfPointsType.ts";
import axios from "axios";
import {useParams} from "react-router-dom";
import GameBox from "./GameBox.tsx";
import Snackbar from "./Snackbar.tsx";

export default function GameResult() {

    const playerId: string = "8162795f-5c82-44fc-a5ef-1cf5ce545f7b"

    const [gameResult, setGameResult] = useState<SetOfPointsType[]>([])
    const [errorMessage, setErrorMessage] = useState<string>("");
    const [showSnackbar, setShowSnackbar] = useState<boolean>(false);

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
                setErrorMessage(error.response?.data?.message || "Ups, looks like something went wrong. Try again or come back later!");
                setShowSnackbar(true);
            })
    }

    function handleCloseSnackbar() {
        setShowSnackbar(false);
    }

    return (
        <>
            {showSnackbar && <Snackbar onClickOk={handleCloseSnackbar} message={errorMessage}/>}
            {gameResult?.map((result) => (
                <GameBox key={result.trashCanId}
                         $text={`${result.playerPoints} / ${result.dataPoints}`}
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
    "1": "var(--trash-color-paper)",
    "2": "var(--trash-color-recycable)",
    "3": "var(--trash-color-rest)",
};
