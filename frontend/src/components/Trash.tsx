import {TrashType} from "../types/TrashType.ts";
import DraggableTrash from "./DraggableTrash.tsx";
import styled from "styled-components";

type TrashProps = {
    trashes: TrashType [];
}

export default function Trash(props: Readonly<TrashProps>) {

    return (
        <StyledSection>
            {props.trashes.map((trash, index) => (
                <DraggableTrash key={trash.id} trash={trash} index={index + 1}/>
            ))
            }
        </StyledSection>
    )
}

const StyledSection = styled.section`
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  gap: 8px;
  //grid-auto-flow: row dense;
  //grid-auto-rows: minmax(0, 1fr);
  grid-template-areas:
    "area1 area1 area2 area2 area3 area3 area4 area4"
    "area1 area1 area2 area2 area3 area3 area4 area4"
    "area1 area1 area2 area2 area7 area7 area4 area4"
    "area5 area5 area2 area2 area7 area7 area9 area9"
    "area5 area5 area6 area6 area7 area7 area9 area9"
    "area5 area5 area6 area6 area8 area8 area9 area9"
    "area5 area5 area6 area6 area8 area8 area9 area9"
    "area11 area11 area11 area10 area10 area12 area12 area12";
`;