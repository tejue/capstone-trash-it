import {useEffect, useState} from "react";
import axios from "axios";
import {TrashType} from "../types/TrashType.ts";

export default function Trash() {

    const [trashes, setTrashes] = useState<TrashType[]>([])

    useEffect(() => {
            getTrashes()
        }, []
    )
    ;

    function getTrashes() {
        axios.get("api/trash")
            .then(response => setTrashes((response.data)))
    }

    console.log(trashes);
    return (
        <>
            {trashes.map((trash) => (
                <section key={trash.id}>
                    <p>{`${trash.name}`}</p>
                </section>))}
        </>
    )
}