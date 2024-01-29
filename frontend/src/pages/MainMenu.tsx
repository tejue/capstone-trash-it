import {useEffect, useState} from "react";
import axios from "axios";
import styled from "styled-components";
import {GamePointsType} from "../types/GamePointsType.ts";
import {useNavigate} from "react-router-dom";
import ButtonBuzzer from "../components/ButtonBuzzer.tsx";

export default function MainMenu() {

    const playerId: string = "8162795f-5c82-44fc-a5ef-1cf5ce545f7b"
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
        const deleteMessage = window.confirm("By clicking 'ok', the score of all games will be deleted. You're sure, this is what you want?");
        if (deleteMessage) {
            axios.put(`/api/game/${playerId}/gamesResult`)
                .then(() => {
                    setAllGamesResult([])
                })
                .catch(error => {
                    console.error("Data could not be deleted:", error)
                })
        }
    }

    function handleStartNewGame() {
        navigate('/game');
    }

    return (
        <>
            {allGamesResult.length === 0 ? (
                <StyledSection1>
                    <GameBox1>You have no saved result so far</GameBox1>
                </StyledSection1>
            ) : (
                <StyledSection>
                    <StyledList><Span>SCORE</Span>
                        {allGamesResult.map((gameResult, index: number) =>
                            <StyledListItem key={index}>
                                <span>Game {index + 1}:</span>
                                <span>{gameResult.playerPointsTotal} / {gameResult.dataPointsTotal}</span>
                            </StyledListItem>
                        )}</StyledList>
                </StyledSection>
            )}
            {allGamesResult.length > 0 && (
                <ButtonBuzzer handleClick={deleteAllGamesResult} buttonText={"fresh start"} color={"red"}
                              $position={"right"}/>)}
            <ButtonBuzzer handleClick={handleStartNewGame} buttonText={"new game"} $position={"left"}/>
        </>
    )
}

const StyledSection = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 30px;
`

const StyledList = styled.ul`
  list-style: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  border: solid 1px #1f1e1e;
  border-radius: 5px;
  height: 60vh;
  width: 200px;
  padding: 20px;
  margin: 20px auto;
  background-color: #9d6101;
  //background-color: #e75b01;
  //color: #1f1f1f;
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.9);
  overflow: auto;
`

const Span = styled.span`
  line-height: 4;
  font-size: 20px;
  font-weight: bold;
  letter-spacing: 2px;
`

const StyledListItem = styled.li`
  display: flex;
  justify-content: space-between;
  margin: 10px auto;
  height: auto;
  width: 100%;
`
const StyledSection1 = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;
`

const GameBox1 = styled.p`
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  line-height: 1.4;
  border: solid 1px #1f1e1e;
  border-radius: 5px;
  padding: 20px;
  background-color: #9d6101;
  width: 200px;
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.9);
`