import {useEffect, useState} from "react";
import axios from "axios";
import styled from "styled-components";
import {GamePointsType} from "../types/GamePointsType.ts";
import {useNavigate} from "react-router-dom";

export default function MainMenu() {

    const playerId: string = "5"
    const navigate = useNavigate();

    const [allGamesResult, setAllGamesResult] = useState<GamePointsType[]>([])

    useEffect(() => {
        getAllGamesResult()
    }, []);

    function getAllGamesResult() {
        axios.get(`/api/game/${playerId}/gamesResult`)
            .then(response => {
                setAllGamesResult(response.data)
            })
            .catch(error => {
                console.error("Request failed: ", error.response);
            })
    }

    function deleteAllGamesResult() {
        axios.put(`/api/game/${playerId}/gamesResult`)
            .then(() => {
                setAllGamesResult([])
            })
            .catch(error => {
                console.error("Data could not be deleted:", error)
            })
    }

    function handleStartNewGame() {
        navigate('/game');
    }

    return (
        <>
            {allGamesResult.length === 0 ? (
                <GameBox>You have no saved result so far</GameBox>
            ) : (
                <>
                    {allGamesResult.map((gameResult, index: number) =>
                        <StyledSection key={index}>
                            <GameBox>Game {index + 1}: </GameBox>
                            <GameBox>{gameResult.playerPointsTotal} / {gameResult.dataPointsTotal}</GameBox>
                        </StyledSection>)}
                    <button onClick={deleteAllGamesResult}>Fresh start: Delete all your results</button>
                </>
            )}
                <button onClick={handleStartNewGame}>Start a new  game</button>
        </>
    )
}

const StyledSection = styled.section`
  display: flex;
  justify-content: space-between;
`
const GameBox = styled.p`
  border: solid 1px;
  border-radius: 5px;
  padding: 20px;
  text-align: center;
`