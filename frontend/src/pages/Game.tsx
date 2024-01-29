import Trash from "../components/Trash.tsx";
import TrashCan from "../components/TrashCan.tsx";
import {TrashType} from "../types/TrashType.ts";
import {TrashCanType} from "../types/TrashCanType.ts";
import {GameType} from "../types/GameType.ts";
import {DndContext, DragEndEvent} from "@dnd-kit/core";
import {useEffect, useState} from "react";
import axios from "axios";
import {PlayerResultType} from "../types/PlayerResultType.ts";
import {useNavigate} from 'react-router-dom';
import styled from "styled-components";

export default function Game() {

    const playerId: string = "8162795f-5c82-44fc-a5ef-1cf5ce545f7b"

    const navigate = useNavigate();

    const [trashes, setTrashes] = useState<TrashType[]>([]);
    const [trashCans, setTrashCans] = useState<TrashCanType[]>([])
    const [playerResult, setPlayerResult] = useState<PlayerResultType>({});
    const [initialTrashes, setInitialTrashes] = useState<boolean>(false);
    const [gameEnd, setGameEnd] = useState<boolean>(false);
    const [games, setGames] = useState<GameType[]>([])

    useEffect(() => {
        if (!initialTrashes) {
            getTrashes();
            setInitialTrashes(true);
        }
        getTrashCans();
    }, [initialTrashes]);

    function getTrashes() {
        axios.get("/api/trash")
            .then(response => {
                setTrashes(response.data)
                axios.put(`/api/game/${playerId}/dataResult`, response.data)
                    .then(response => {
                        setGames(response.data.games)
                    })
            })
            .catch(error => {
                console.error("Request failed: ", error);
            });
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

    function postPlayerResult() {
        axios.put(`/api/game/${playerId}/` + games.length, playerResult)
            .catch(error => {
                console.error("data could not be transmitted:", error);
            })
            .finally(() => {
                navigate('/game-result/' + games.length);
            });
    }

    function handleDragEnd(event: DragEndEvent) {
        const {active, over} = event

        if (active && over) {
            setPlayerResult((prevResults) => {
                const newResults = {...prevResults};
                newResults[over.id] = {
                    trashCanId: over.id as string,
                    trashIds: [...(newResults[over.id]?.trashIds || []), active.id as string],
                };
                return newResults;
            });
            setTrashes((prevTrashes) => {
                const trashToRecycle = [...prevTrashes];

                const draggedTrashIndex = prevTrashes.findIndex(trash => trash.id === active.id);
                if (draggedTrashIndex !== -1) {
                    trashToRecycle.splice(draggedTrashIndex, 1);
                    trashToRecycle.splice(draggedTrashIndex, 0, {
                        id: `placeholder-${draggedTrashIndex}`,
                        name: "",
                        image: "",
                        trashType: ""
                    });
                }

                handleGameEnd(trashToRecycle);
                return trashToRecycle;
            })
        }
    }

    function handleGameEnd(trashToRecycle: TrashType[]) {
        const noLeftTrashes = trashToRecycle.every(trash => trash.name === "");
        setGameEnd(noLeftTrashes)
    }

    return (
        <DndContext
            onDragEnd={handleDragEnd}
        >
            {gameEnd ? (
                <>
                    <StyledSection>
                        <GameBox>Well Done! All trash is sorted.</GameBox>
                    </StyledSection>
                    <ButtonContainer>
                        <BuzzerButton onClick={postPlayerResult}><StyledSpan>see your result</StyledSpan></BuzzerButton>
                    </ButtonContainer>
                </>
            ) : (
                <>
                    <Trash trashes={trashes}/>
                    <TrashCan trashCans={trashCans}/>
                </>)}
        </DndContext>
    )
}

const StyledSection = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 70vh;
`

const GameBox = styled.p`
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
  border: 1px solid #015701;
  border-radius: 50%;
  background: linear-gradient(180deg, #013b01, #026002);
  font-size: 16px;
  color: #c0bdbd;
`
