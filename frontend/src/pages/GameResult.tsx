import {useEffect, useState} from "react";
import axios from "axios";
import {SetOfPointsType} from "../types/SetOfPointsType.ts";
import styled from "styled-components";
import {useNavigate, useParams} from "react-router-dom";

export default function GameResult() {

    const playerId: string = "8162795f-5c82-44fc-a5ef-1cf5ce545f7b"

    const {gameId} = useParams();
    const navigate = useNavigate();

    const [gameResult, setGameResult] = useState<SetOfPointsType[]>([])

    useEffect(() => {
        getGameResult();
    },);

    function getGameResult() {
        axios.get(`/api/game/${playerId}/${gameId}/gameResult`)
            .then(response => {
                setGameResult(response.data.setOfPoints)
            })
            .catch(error => {
                console.error("Request failed: ", error);
            })
    }

    function handleNextPage() {
        navigate('/main-menu');
    }

    return (
        <>
            <StyledSection>
                {gameResult?.map((result) => (
                    <GameBox key={result.trashCanId}
                             bgcolor={getBackgroundColor(result.trashCanId)}>{result.playerPoints} / {result.dataPoints}</GameBox>
                ))}
            </StyledSection>
            <button onClick={handleNextPage}>Go to Main Menu</button>
        </>
    );
}

type GameBoxProps = {
    bgcolor: string;
}

const trashCanColors: {[key: string]: string } = {
    "1": "#0071BC",
    "2": "#F7931E",
    "3": "#333333",
};

const getBackgroundColor = (trashCanId: string) =>
    trashCanColors[trashCanId] || "default";

const StyledSection = styled.section`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`

const GameBox = styled.p<GameBoxProps>`
  text-align: center;
  border: solid 1px;
  border-radius: 5px;
  height: 20vh;
  width: 200px;
  margin: 20px auto;
  background-color: ${(props) => props.bgcolor};
`