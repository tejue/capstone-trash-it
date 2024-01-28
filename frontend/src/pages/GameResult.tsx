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
                             style={{backgroundColor: getBackgroundColor(result.trashCanId)}}
                    >{result.playerPoints} / {result.dataPoints}</GameBox>
                ))}
            </StyledSection>
            <ButtonContainer>
                <BuzzerButton onClick={handleNextPage}>
                    <StyledSpan>main menue</StyledSpan>
                </BuzzerButton>
            </ButtonContainer>
        </>
    );
}

type GameBoxProps = {
    bgcolor?: string;
}

const trashCanColors: { [key: string]: string } = {
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
  display: flex;
  justify-content: center;
  align-items: center;
  border: solid 1px #1f1e1e;
  border-radius: 5px;
  height: 15vh;
  width: 200px;
  margin: 20px auto;
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.9);
  ${(props) => props.bgcolor && `background-color: ${props.bgcolor};`}
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
