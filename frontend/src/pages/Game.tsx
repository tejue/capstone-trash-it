import Trash from "../components/Trash.tsx";
import TrashCan from "../components/TrashCan.tsx";
import {TrashType} from "../types/TrashType.ts";
import {TrashCanType} from "../types/TrashCanType.ts";
import {DndContext, DragEndEvent} from "@dnd-kit/core";
import {useEffect, useState} from "react";
import axios from "axios";
import {PlayerResultType} from "../types/PlayerResultType.ts";

export default function Game() {

    const [trashes, setTrashes] = useState<TrashType[]>([]);
    const [trashCans, setTrashCans] = useState<TrashCanType[]>([])
    const [playerResult, setPlayerResult] = useState<PlayerResultType>({});

    const [initialTrashes, setInitialTrashes] = useState<boolean>(false);
    const [gameEnd, setGameEnd] = useState<boolean>(false);

    useEffect(() => {
        if (!initialTrashes) {
            getTrashes();
            setInitialTrashes(true);
        }
        getTrashCans();
    }, [initialTrashes]);

    function getTrashes() {
        axios.get("api/trash")
            .then(response => {
                setTrashes(response.data)
            })
            .catch(error => {
                console.error("Request failed: ", error);
            });
    }

    function getTrashCans() {
        axios.get("api/trashcan")
            .then(response => {
                setTrashCans(response.data)
            })
            .catch(error => {
                console.error("Request failed: ", error);
            });
    }

    const playerId: string = "5"
    const gameId: string = "1"

    function postPlayerResult() {
        axios.post(`/api/game/${playerId}/${gameId}`, playerResult)
            .catch(error => {
                console.error("data could not be transmitted:", error);
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
                const trashToRecycle = prevTrashes.filter((trash) => trash.id !== active.id)
                handleGameEnd(trashToRecycle);
                return trashToRecycle;
            });
        }
    }

    function handleGameEnd(trashToRecycle: TrashType []) {
        if (trashToRecycle.length === 0) setGameEnd(true)
    }

    return (
        <DndContext
            onDragEnd={handleDragEnd}
        >
            {gameEnd ? (
                <>
                    <p>Well Done! All trash is sorted.</p>
                    <button onClick={postPlayerResult}>See your result</button>
                </>
            ) : (
                <>
                    <Trash trashes={trashes}/>
                    <TrashCan trashCans={trashCans}/>
                </>)}
        </DndContext>
    )
}
