import {useEffect, useState} from "react";
import axios from "axios";
import styled from "styled-components";
import {GamePointsType} from "../types/GamePointsType.ts";
import {useNavigate} from "react-router-dom";

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
                <StyledSection1>
                    <GameBox1>You have no saved result so far</GameBox1>
                </StyledSection1>
            ) : (
                <>
                    {allGamesResult.map((gameResult, index: number) =>
                        <StyledSection key={index}>
                            <GameBox>Game {index + 1}: </GameBox>
                            <GameBox>{gameResult.playerPointsTotal} / {gameResult.dataPointsTotal}</GameBox>
                        </StyledSection>)}
                    <ButtonContainer1>
                        <BuzzerButton1 onClick={deleteAllGamesResult}><StyledSpan1>fresh
                            start</StyledSpan1></BuzzerButton1>
                    </ButtonContainer1>
                </>
            )}
            <ButtonContainer>
                <BuzzerButton onClick={handleStartNewGame}><StyledSpan>new game</StyledSpan></BuzzerButton>
            </ButtonContainer>
        </>
    )
}

const StyledSection = styled.section`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 20px;
  margin: 30px;
`
const GameBox = styled.p`
  display: flex;
  justify-content: center;
  align-items: center;
  border: solid 1px #1f1e1e;
  border-radius: 5px;
  padding: 20px;
  background-color: #9d6101;
  height: 10vh;
  width: 200px;
  margin: 20px auto;
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.9);
`

const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  margin: 40px 30px;
  position: fixed;
  bottom: 0;
  right: 0;
`
const BuzzerButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100px;
  width: 100px;
  overflow: hidden;
  border-radius: 50%;
  border: 1px solid #1f1e1e;
  //background: linear-gradient(0deg, #1a1919, #3b3a3a);
  background: linear-gradient(0deg, #013b01, #026002);
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.9);
  transition: box-shadow 0.2s;

  &:hover {
    box-shadow: 0 2px 5px rgb(0, 0, 0, 0.3);
  }
`

const StyledSpan = styled.span`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80px;
  width: 80px;
  //border: 1px solid #1f1e1e;
  border: 1px solid #015701;
  border-radius: 50%;
  //background: linear-gradient(180deg, #1a1919, #3b3a3a);
  background: linear-gradient(180deg, #013b01, #026002);
  font-size: 16px;
  color: #c0bdbd;
`

const ButtonContainer1 = styled.div`
  display: flex;
  justify-content: flex-end;
  margin: 40px 30px;
  position: fixed;
  bottom: 0;
  left: 0;
`
const BuzzerButton1 = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100px;
  width: 100px;
  overflow: hidden;
  border-radius: 50%;
  border: 1px solid #1f1e1e;
  //background: linear-gradient(0deg, #1a1919, #3b3a3a);
  background: linear-gradient(0deg, #560018, #9f0225);
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.9);
  transition: box-shadow 0.2s;

  &:hover {
    box-shadow: 0 2px 5px rgb(0, 0, 0, 0.3);
  }
`

const StyledSpan1 = styled.span`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80px;
  width: 80px;
  //border: 1px solid #1f1e1e;
  border: 1px solid #810123;
  border-radius: 50%;
  //background: linear-gradient(180deg, #1a1919, #3b3a3a);
  background: linear-gradient(180deg, #560018, #9f0225);
  font-size: 16px;
  color: #c0bdbd;
`
const StyledSection1 = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 30px;
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