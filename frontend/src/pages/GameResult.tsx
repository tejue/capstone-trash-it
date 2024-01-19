import {useEffect, useState} from "react";
import axios from "axios";
import {SetOfPointsType} from "../types/SetOfPointsType.ts";
import styled from "styled-components";

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

    return(
        <>
            {gameResult?.map((result) => (
                <StyledSection key={result.trashCanId}>
                    <GameBox bgcolor={getBackgroundColor(result.trashCanId)}>{result.playerPoints} / {result.dataPoints}</GameBox>
                </StyledSection>
            ))}
        </>
    );
}

type GameBoxProps = {
    bgcolor: string;
}

const StyledSection = styled.section`
  display: flex;
  justify-content: space-between;
`
const getBackgroundColor = (trashCanId: string) =>
    trashCanId === "1" ? "blue" : trashCanId === "2" ? "yellow" : trashCanId === "3" ? "grey" : "default";

const GameBox = styled.p<GameBoxProps>`
  border: solid 1px;
  border-radius: 5px;
  padding: 20px;
  text-align: center;
  background-color: ${(props) => props.bgcolor};
`