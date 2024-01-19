import {useEffect, useState} from "react";
import axios from "axios";
import styled from "styled-components";
import {GameResultType} from "../types/GameResultType.ts";

export default function MainMenu() {

    const [games, setGames] = useState<GameResultType[]>([])

    const playerId: string = "2"

    useEffect(() => {
        getAllGames()
    }, []);

    function getAllGames() {
        axios.get(`/api/game/${playerId}/games`)
            .then(response => {
                setGames(response.data);
            })
            .catch(error => {
                console.error("Request failed: ", error);
            });
    }

    return (
        <>
            {games.length === 0 ? (
                <GameBox>You have no saved result so far</GameBox>
            ) : (
                games.map((game) => (
                    <StyledSection key={game.gameId}>
                        <GameBox>{`Round: ${game.gameId}`}</GameBox>
                        <GameBox>{`You trashed ${game.gameTotal} / ${game.dataTotal}`}</GameBox>
                    </StyledSection>
                )))}
        </>
    );
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