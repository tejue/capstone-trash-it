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
            <BuzzerButton onClick={handleNextPage}>button</BuzzerButton>
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
  border: solid 1px;
  border-radius: 5px;
  height: 20vh;
  width: 200px;
  margin: 20px auto;
  ${(props) => props.bgcolor && `background-color: ${props.bgcolor};`}
`
const BuzzerButton = styled.button`
  position: relative;
  outline: none;
  font-size: 1.5rem;
  color: rgb(137, 180, 205);
  text-transform: uppercase;
  padding: 1em 2em;
  border: 2px solid rgb(137, 180, 205);
  border-radius: 1em;
  //border-radius: 50%;
  //height: 100px;
  //width: 50px;
  background: rgb(232, 251, 255);
  transform-style: preserve-3d;
  transition: all 175ms cubic-bezier(0, 0, 1, 1);

  &::before {
    position: absolute;
    content: "";
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    background: rgb(195, 230, 242);
    border-radius: inherit;
    box-shadow: 0 0 0 2px rgb(156, 198, 205), 0 0.75em 0 0 rgb(137, 180, 205);
    transform: translate3d(0, 0.75em, -1em);
    transition: all 175ms cubic-bezier(0, 0, 1, 1);
  }

  &:hover {
    background: rgb(211, 243, 251);
    transform: translate(0, 0.375em);
  }

  &:active {
    transform: translate(0em, 0.75em);
  }

  &:active::before {
    transform: translate3d(0, 0, -1em);
    box-shadow: 0 0 0 2px rgb(137, 180, 205), 0 0.25em 0 0 rgb(137, 180, 205);
  }
`