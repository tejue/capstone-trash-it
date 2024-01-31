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
  position: absolute;
  width: 100%;
  height: 45vh;
  top: 0;
  transform: translate(0, 40%);
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  gap: 8px;
  grid-auto-flow: row dense;
  grid-auto-rows: minmax(0, 1fr);
  grid-template-areas:
    "area1 area1 area101 area102 area3 area3 area103 area104"
    "area1 area1 area105 area106 area3 area3 area4 area4"
    "area1 area1 area2 area2 area3 area3 area4 area4"
    "area1 area1 area2 area2 area3 area3 area4 area4"
    "area5 area5 area2 area2 area8 area8 area4 area4"
    "area5 area5 area2 area2 area8 area8 area107 area108"
    "area5 area5 area6 area6 area8 area8 area109 area110"
    "area5 area5 area6 area6 area8 area8 area9 area9"
    "area7 area7 area6 area6 area10 area10 area9 area9"
    "area7 area7 area6 area6 area10 area10 area9 area9"
    "area7 area7 area113 area114 area10 area10 area9 area9"
    "area7 area7 area115 area116 area10 area10 area117 area118"
`
