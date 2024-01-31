import Trash from "../components/Trash.tsx";
import TrashCan from "../components/TrashCan.tsx";
import {TrashType} from "../types/TrashType.ts";
import {TrashCanType} from "../types/TrashCanType.ts";
import {GameType} from "../types/GameType.ts";
import {DndContext, DragEndEvent, PointerSensor, TouchSensor, useSensor, useSensors} from "@dnd-kit/core";
import {useEffect, useState} from "react";
import axios from "axios";
import {PlayerResultType} from "../types/PlayerResultType.ts";
import {useNavigate} from 'react-router-dom';
import styled from "styled-components";
import ButtonBuzzer from "../components/ButtonBuzzer.tsx";
import windsGrey from "../assets/windsGrey.svg";
import {Background} from "../components/Background.ts";

export default function Game() {

    const playerId: string = "8162795f-5c82-44fc-a5ef-1cf5ce545f7b"

    const navigate = useNavigate();
    const sensors = useSensors(
        useSensor(PointerSensor),
        useSensor(TouchSensor),
    )

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
        <>
            <Background/>
            {!gameEnd && <StyledImage src={windsGrey} alt={"winds"}/>}
            <DndContext
                sensors={sensors}
                onDragEnd={handleDragEnd}
            >
                {gameEnd ? (
                    <>
                        <StyledSection>
                            <GameBox>Well Done! All trash is sorted.</GameBox>
                        </StyledSection>
                        <ButtonBuzzer handleClick={postPlayerResult} buttonText={"see your result"}
                                      $position={"right"}/>
                    </>
                ) : (
                    <StyledDivGameArea>
                        <Trash trashes={trashes}/>
                        <TrashCan trashCans={trashCans}/>
                    </StyledDivGameArea>
                )}
            </DndContext>
        </>
    )
}

const StyledDivGameArea = styled.div`
  display: flex;
  flex-direction: column;
  position: relative;
  height: 100vh;
`

const StyledImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
`

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
  border: solid 1px #e4f0e9;
  border-radius: 5px;
  padding: 20px;
  background-color: #E6F0E9;
  width: 300px;
  height: 300px;
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.9);
  clip-path: polygon(50% 0%, 90% 20%, 100% 50%, 100% 80%, 60% 100%, 20% 90%, 0% 60%, 10% 25%);
`
