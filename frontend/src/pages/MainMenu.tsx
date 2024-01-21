import {useEffect, useState} from "react";
import axios from "axios";
import styled from "styled-components";
import {GamePoints} from "../types/GamePoints.ts";

export default function MainMenu() {

    const playerId: string = "5"

    const [allGamesResult, setAllGamesResult] = useState<GamePoints[]>([])

    useEffect(() => {
        getAllGamesResult()
    }, []);

    function getAllGamesResult() {
        axios.get(`/api/game/${playerId}/gamesResult`)
            .then(response => {
                setAllGamesResult(response.data)
                console.log("new" + response.data)
            })
            .catch(error => {
                console.error("Request failed: ", error);
            })
    }

    return (
        <>
            {allGamesResult.length === 0 ? (
                <GameBox>You have no saved result so far</GameBox>
            ) : (
                allGamesResult.map((gameResult, index: number) =>
                    <StyledSection key={index}>
                        <GameBox>Game {index + 1}: </GameBox>
                        <GameBox>{gameResult.playerPointsTotal} / {gameResult.dataPointsTotal}</GameBox>
                    </StyledSection>))}
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