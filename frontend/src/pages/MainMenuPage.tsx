import {useEffect, useState} from "react";
import axios from "axios";
import styled from "styled-components";
import {GamePointsType} from "../types/GamePointsType.ts";
import {useNavigate} from "react-router-dom";
import ButtonBuzzer from "../components/ButtonBuzzer.tsx";
import {Background} from "../components/Background.ts";
import GameBox from "../components/GameBox.tsx";
import Snackbar from "../components/Snackbar.tsx";

export default function MainMenuPage() {

    const playerId: string = "8162795f-5c82-44fc-a5ef-1cf5ce545f7b"
    const navigate = useNavigate();

    const [allGamesResult, setAllGamesResult] = useState<GamePointsType[]>([])
    const [errorMessage, setErrorMessage] = useState<string>("");
    const [showSnackbar, setShowSnackbar] = useState<boolean>(false);

    useEffect(() => {
        getAllGamesResult()
    }, []);

    function getAllGamesResult() {
        axios.get(`/api/game/${playerId}/gamesResult`)
            .then(response => {
                setAllGamesResult(response.data)
            })
            .catch(error => {
                console.error("Request failed: ", error.response.status)
                setErrorMessage(error.response?.data?.message || "Ups, looks like something went wrong. Try again or come back later!");
                setShowSnackbar(true);
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
                    console.error("Data could not be deleted:", error.response.status)
                    setErrorMessage(error.response?.data?.message || "Ups, looks like something went wrong. Try again or come back later!");
                    setShowSnackbar(true);
                })
        }
    }

    function handleCloseSnackbar() {
        setShowSnackbar(false);
    }

    function handleStartNewGame() {
        navigate('/game');
    }

    return (
        <>
            <Background/>
            {showSnackbar && <Snackbar onClick={handleCloseSnackbar} message={errorMessage}/>}
            {allGamesResult.length === 0 ? (
                <StyledGameBoxSection>
                    <GameBox $text={"You have no saved result so far"}/>
                </StyledGameBoxSection>
            ) : (
                <StyledListSection>
                    <StyledList><StyledHeading>Score</StyledHeading>
                        {allGamesResult.map((gameResult, index: number) =>
                            <StyledListItem key={index}>
                                <span>Game {index + 1}:</span>
                                <span>{gameResult.playerPointsTotal} / {gameResult.dataPointsTotal}</span>
                            </StyledListItem>
                        )}</StyledList>
                </StyledListSection>
            )}
            <StyledDivButtonsPosition>
                {allGamesResult.length > 0 && (
                    <ButtonBuzzer handleClick={deleteAllGamesResult} buttonText={"fresh start"} color={"red"}
                                  $position={"left"}
                    />)}
                <ButtonBuzzer handleClick={handleStartNewGame} buttonText={"new game"} $position={"right"}/>
            </StyledDivButtonsPosition>
        </>
    )
}

const StyledGameBoxSection = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;
`

const StyledListSection = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;
  margin: 30px;
`

const StyledList = styled.ul`
  list-style: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 50vh;
  width: 350px;
  padding: 40px 80px;
  margin: 20px auto;
  background-color: var(--secondary-color);
  overflow: auto;
  clip-path: polygon(50% 0%, 90% 20%, 100% 50%, 100% 80%, 60% 100%, 10% 90%, 0% 60%, 10% 25%);
`

const StyledHeading = styled.h2`
  font-size: 1.3rem;
  font-weight: bold;
  letter-spacing: 2px;
  text-transform: uppercase;
`

const StyledListItem = styled.li`
  display: flex;
  justify-content: space-between;
  margin: 10px auto;
  height: auto;
  width: 100%;
`

const StyledDivButtonsPosition = styled.div`
  display: flex;
`
