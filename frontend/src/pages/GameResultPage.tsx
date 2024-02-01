import {useEffect, useState} from "react";
import axios from "axios";
import {SetOfPointsType} from "../types/SetOfPointsType.ts";
import styled from "styled-components";
import {useNavigate, useParams} from "react-router-dom";
import ButtonBuzzer from "../components/ButtonBuzzer.tsx";
import {Background} from "../components/Background.ts";
import {TrashCanType} from "../types/TrashCanType.ts";

export default function GameResultPage() {

    const playerId: string = "8162795f-5c82-44fc-a5ef-1cf5ce545f7b"

    const {gameId} = useParams();
    const navigate = useNavigate();

    const [gameResult, setGameResult] = useState<SetOfPointsType[]>([])
    const [trashCans, setTrashCans] = useState<TrashCanType[]>([])

    useEffect(() => {
        getGameResult()
        getTrashCans();
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

    function getTrashCans() {
        axios.get("/api/trashcan")
            .then(response => {
                setTrashCans(response.data)
            })
            .catch(error => {
                console.error("Request failed: ", error);
            });
    }

    function handleNextPage() {
        navigate('/main-menu');
    }

    return (
        <>
            <Background/>
            <StyledDivResult>
                <StyledSection>
                    {gameResult?.map((result) => (
                        <GameBox key={result.trashCanId}
                                 style={{backgroundColor: getBackgroundColor(result.trashCanId)}}
                        >{result.playerPoints} / {result.dataPoints}</GameBox>
                    ))}
                </StyledSection>
                <ImageContainer>
                    {trashCans?.map((trashCan) => (
                        <StyledImage key={trashCan.id} src={`${trashCan.image}`} alt={`${trashCan.name}`}/>))}
                </ImageContainer>
            </StyledDivResult>
            <ButtonBuzzer handleClick={handleNextPage} buttonText={"main menu"}/>
        </>
    );
}

const getBackgroundColor = (trashCanId: string) =>
    trashCanColors[trashCanId] || "default";

type GameBoxProps = {
    bgcolor?: string;
}

const trashCanColors: { [key: string]: string } = {
    "1": "#0071BC",
    "2": "#F7931E",
    "3": "#333333",
};

const StyledDivResult = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translate(-50%, -50%);
`

const StyledSection = styled.section`
  display: flex;
  justify-content: space-evenly;
  width: 100%;
`

const GameBox = styled.p<GameBoxProps>`
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  line-height: 1.4;
  color: #E6F0E9;
  ${(props) => props.bgcolor && `background-color: ${props.bgcolor};`}
  margin: auto 5px;
  padding: 20px;
  border-radius: 5px;
  width: 100px;
  height: 100px;
  clip-path: polygon(50% 0%, 90% 20%, 100% 50%, 100% 80%, 60% 100%, 20% 90%, 0% 60%, 10% 25%);
`

const ImageContainer = styled.div`
  display: flex;
  height: 32vh;
  overflow: hidden;
`

const StyledImage = styled.img`
  height: 100%;
`
